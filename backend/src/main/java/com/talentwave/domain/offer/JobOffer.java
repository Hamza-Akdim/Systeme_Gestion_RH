package com.talentwave.domain.offer;

import com.talentwave.domain.enumeration.Contrat;
import com.talentwave.domain.enumeration.Secteur;
import com.talentwave.domain.hr.JobProfile;
import jakarta.persistence.*;
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

@Entity
@Table(name = "job_offer")
@Getter
@Setter
@NoArgsConstructor
public class JobOffer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String title;

    @Lob
    private String description;

    @NotNull
    private Contrat contrat;

    @NotNull
    private Secteur secteur;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "joboffer_hardskill",
            joinColumns = @JoinColumn(name = "job_offer_id"),
            inverseJoinColumns = @JoinColumn(name = "hard_skill_id")
    )
    private List<HardSkill> hardSkills;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OfferStatus status = OfferStatus.OPEN; // Default status

    @NotNull
    @FutureOrPresent
    private LocalDate closingDate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "jobOffer_id", referencedColumnName = "id", nullable = false)
    private List<TaskMission> taskMissions;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    // Enum for OfferStatus
    public enum OfferStatus {
        OPEN, FILLED, CLOSED, ON_HOLD
    }

    // @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "job_profile_id")
//    private JobProfile jobProfile; // Link to a JobProfile
}

