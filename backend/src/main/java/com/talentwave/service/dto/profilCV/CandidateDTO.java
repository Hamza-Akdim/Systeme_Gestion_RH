package com.talentwave.service.dto.profilCV;

import com.talentwave.domain.profilCV.ProfileCV;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class CandidateDTO {
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @NotBlank
    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 20)
    private String phoneNumber;

    private String resumeUrl;

    private String coverLetterText;

    private String linkedInProfileUrl;

    private List<ProfileCVDTO> profileCVDTOs;

    private Instant createdAt;

    private Instant updatedAt;
}

