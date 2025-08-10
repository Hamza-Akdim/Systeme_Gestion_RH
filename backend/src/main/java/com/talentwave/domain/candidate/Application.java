package com.talentwave.domain.candidate;

import com.talentwave.domain.offer.JobOffer;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "application")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "candidate_id")
    private Candidate candidate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_offer_id")
    private JobOffer jobOffer;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ApplicationStatus status = ApplicationStatus.APPLIED; // Default status

    @NotNull
    private LocalDate applicationDate = LocalDate.now();

    @Lob
    private String notes; // Internal notes about the application

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    // Enum for ApplicationStatus
    public enum ApplicationStatus {
        APPLIED,
        SCREENING,
        INTERVIEWING,
        OFFERED,
        HIRED,
        REJECTED,
        WITHDRAWN
    }

    // Le constructeur par défaut est déjà fourni par l'annotation @NoArgsConstructor

    public Application(Candidate candidate, JobOffer jobOffer) {
        this.candidate = candidate;
        this.jobOffer = jobOffer;
    }
    
    // Getters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Candidate getCandidate() {
        return candidate;
    }
    
    public void setCandidate(Candidate candidate) {
        this.candidate = candidate;
    }
    
    public JobOffer getJobOffer() {
        return jobOffer;
    }
    
    public void setJobOffer(JobOffer jobOffer) {
        this.jobOffer = jobOffer;
    }
    
    public ApplicationStatus getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        try {
            this.status = ApplicationStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            // Fallback to default status if invalid
            this.status = ApplicationStatus.APPLIED;
        }
    }
    
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
    
    public LocalDate getApplicationDate() {
        return applicationDate;
    }
    
    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
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

