package com.talentwave.domain.sourcing;

import com.talentwave.domain.hr.JobProfile; // Assuming a JobOffer is linked to a JobProfile
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

    @NotBlank
    @Size(max = 100)
    private String location;

    @NotNull
    @Enumerated(EnumType.STRING)
    private OfferStatus status = OfferStatus.OPEN; // Default status

    @NotNull
    @FutureOrPresent
    private LocalDate closingDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_profile_id")
    private JobProfile jobProfile; // Link to a JobProfile

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    // Enum for OfferStatus
    public enum OfferStatus {
        OPEN, FILLED, CLOSED, ON_HOLD
    }

    public JobOffer(String title, String description, String location, LocalDate closingDate, JobProfile jobProfile) {
        this.title = title;
        this.description = description;
        this.location = location;
        this.closingDate = closingDate;
        this.jobProfile = jobProfile;
    }
    
    // Getters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public OfferStatus getStatus() {
        return status;
    }
    
    public void setStatus(OfferStatus status) {
        this.status = status;
    }
    
    public LocalDate getClosingDate() {
        return closingDate;
    }
    
    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }
    
    public JobProfile getJobProfile() {
        return jobProfile;
    }
    
    public void setJobProfile(JobProfile jobProfile) {
        this.jobProfile = jobProfile;
    }
    
    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}

