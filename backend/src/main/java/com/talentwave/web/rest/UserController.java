package com.talentwave.web.rest;

import com.talentwave.service.UserService;
import com.talentwave.service.dto.UserCreateDTO;
import com.talentwave.service.dto.UserDTO;
import com.talentwave.service.dto.UserUpdateDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final Logger log = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * {@code POST  /api/users} : Create a new user.
     *
     * @param userCreateDTO the user to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new userDTO, or with status {@code 400 (Bad Request)} if the user has already an ID.
     */
    @PostMapping
//    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        UserDTO result = userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/users/:id} : Updates an existing user.
     *
     * @param id the id of the userDTO to save.
     * @param userUpdateDTO the userDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated userDTO,
     * or with status {@code 400 (Bad Request)} if the userDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the userDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO userUpdateDTO) {
        log.debug("REST request to update User : {}, {}", id, userUpdateDTO);
        Optional<UserDTO> updatedUser = userService.updateUser(id, userUpdateDTO);
        return updatedUser.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/users} : get all the users.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of users in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('HR')")
    public ResponseEntity<List<UserDTO>> getAllUsers(Pageable pageable) {
        log.debug("REST request to get a page of Users");
        Page<UserDTO> page = userService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/users/:id} : get the "id" user.
     *
     * @param id the id of the userDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the userDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('HR') or @userService.isCurrentUser(principal, #id)")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        log.debug("REST request to get User : {}", id);
        Optional<UserDTO> userDTO = userService.findOne(id);
        return userDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/users/:id} : delete the "id" user.
     *
     * @param id the id of the userDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        log.debug("REST request to delete User : {}", id);
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

