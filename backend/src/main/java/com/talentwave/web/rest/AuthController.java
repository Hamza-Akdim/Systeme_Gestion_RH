package com.talentwave.web.rest;

import com.talentwave.domain.User;
import com.talentwave.domain.enumeration.Role;
import com.talentwave.repository.UserRepository;
import com.talentwave.security.jwt.JwtUtils;
import com.talentwave.security.services.UserDetailsImpl;
import com.talentwave.web.rest.vm.LoginRequest;
import com.talentwave.web.rest.vm.JwtResponse;
import com.talentwave.service.dto.UserDTO; // Will be created later, for now, use a simple map or User object

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository; // For /me endpoint, and potentially initial user creation if needed

    @Autowired
    PasswordEncoder encoder; // For initial user creation if needed, or password changes

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);
        
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();        
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                                                 userDetails.getId(), 
                                                 userDetails.getUsername(), 
                                                 userDetails.getEmail(), 
                                                 roles));
    }

    @GetMapping("/me")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || "anonymousUser".equals(authentication.getPrincipal())) {
            return ResponseEntity.status(401).body("User not authenticated");
        }
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        // For now, returning a simple map. Later, this should return a UserDTO.
        // This is a placeholder until UserDTO is defined.
        UserDTO userDTO = new UserDTO();
        userDTO.setId(userDetails.getId());
        userDTO.setUsername(userDetails.getUsername());
        userDTO.setEmail(userDetails.getEmail());
        userDTO.setRole(Role.valueOf(userDetails.getAuthorities().iterator().next().getAuthority())); // Assuming one role
        userDTO.setEnabled(userDetails.isEnabled());
        // userDTO.setCreatedAt( ... ); // Need to fetch the full User entity for this

        return ResponseEntity.ok(userDTO);
    }
}

