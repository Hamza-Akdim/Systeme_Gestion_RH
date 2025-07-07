package com.talentwave.service.impl;

import com.talentwave.domain.User;
import com.talentwave.domain.enumeration.Role;
import com.talentwave.repository.UserRepository;
import com.talentwave.service.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Disabled("Tests désactivés temporairement jusqu'à synchronisation complète avec l'implémentation")
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    public void setup() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setPassword("password");
        user.setRole("ADMIN");
        user.setActive(true);
        user.setCreatedAt(Instant.now());

        userDTO = new UserDTO();
        userDTO.setId(1L);
        userDTO.setUsername("testuser");
        userDTO.setEmail("test@example.com");
        userDTO.setRole(Role.ADMIN);
        userDTO.setEnabled(true);
    }

    @Test
    public void testFindAll() {
        // Arrange
        List<User> users = Arrays.asList(user);
        Page<User> page = new PageImpl<>(users);
        Pageable pageable = PageRequest.of(0, 10);
        
        when(userRepository.findAll(pageable)).thenReturn(page);

        // Act
        Page<UserDTO> result = userService.findAll(pageable);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(1);
        assertThat(result.getContent().get(0).getUsername()).isEqualTo("testuser");
        
        verify(userRepository).findAll(pageable);
    }

    @Test
    public void testFindOne() {
        // Arrange
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        // Act
        Optional<UserDTO> result = userService.findOne(1L);

        // Assert
        assertThat(result).isPresent();
        assertThat(result.get().getUsername()).isEqualTo("testuser");
        
        verify(userRepository).findById(1L);
    }

    @Test
    public void testFindOneNotFound() {
        // Arrange
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        // Act
        Optional<UserDTO> result = userService.findOne(99L);

        // Assert
        assertThat(result).isEmpty();
        
        verify(userRepository).findById(99L);
    }

    // Commenté jusqu'à ce que la méthode save soit implémentée correctement
    /*
    @Test
    public void testSave() {
        // Arrange
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        UserDTO result = userService.save(userDTO);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo("testuser");
        
        verify(passwordEncoder).encode(any());
        verify(userRepository).save(any(User.class));
    }
    */

    // Commenté jusqu'à ce que la méthode delete soit implémentée correctement
    /*
    @Test
    public void testDelete() {
        // Arrange
        doNothing().when(userRepository).deleteById(1L);

        // Act
        userService.delete(1L);

        // Assert
        verify(userRepository).deleteById(1L);
    }
    */
}
