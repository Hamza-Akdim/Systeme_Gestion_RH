package com.talentwave.service.dto;

import com.talentwave.domain.enumeration.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {

    private Long id;

    @NotBlank
    @Size(max = 50)
    private String username;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    @NotNull
    private Role role;

    private boolean enabled;

    private Instant createdAt;

    // Constructor, getters, and setters are handled by Lombok
    // Add any other necessary fields or methods
}

