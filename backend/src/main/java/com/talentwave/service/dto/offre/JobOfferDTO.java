package com.talentwave.service.dto.offre;

import com.talentwave.domain.enumeration.Contrat;
import com.talentwave.domain.enumeration.Secteur;
import com.talentwave.domain.offer.JobOffer.OfferStatus;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class JobOfferDTO {
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String title;

    private String description;

    @NotNull
    private Contrat contrat;

    @NotNull
    private Secteur secteur;

    private List<HardSkillDTO> hardSkills;

    @NotNull
    private OfferStatus status;

    @NotNull
    @FutureOrPresent
    private LocalDate closingDate;

    private List<TaskMissionDTO> taskMissions;

    private Instant createdAt;

    private Instant updatedAt;

}

