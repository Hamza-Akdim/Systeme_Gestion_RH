package com.talentwave.domain.onboarding;

import com.talentwave.domain.User; // Assuming a User (Consultant) to whom this checklist applies
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "consultant_checklist")
@Getter
@Setter
@NoArgsConstructor
public class ConsultantChecklist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private User consultant; // The consultant being onboarded

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "onboarding_step_id")
    private OnboardingStep onboardingStep;
    
    // Champs ajoutés pour correspondre aux appels dans le service
    private String taskName;
    
    private String description;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "verified_by_id")
    private User verifiedBy;

    private boolean completed = false;

    private LocalDate completionDate;

    @Lob
    private String notes;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    public ConsultantChecklist(User consultant, OnboardingStep onboardingStep) {
        this.consultant = consultant;
        this.onboardingStep = onboardingStep;
    }
    
    // Getters et setters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getConsultant() {
        return consultant;
    }
    
    public void setConsultant(User consultant) {
        this.consultant = consultant;
    }
    
    public Long getConsultantId() {
        return consultant != null ? consultant.getId() : null;
    }
    
    public OnboardingStep getOnboardingStep() {
        return onboardingStep;
    }
    
    public void setOnboardingStep(OnboardingStep onboardingStep) {
        this.onboardingStep = onboardingStep;
    }
    
    public String getTaskName() {
        return taskName;
    }
    
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public User getVerifiedBy() {
        return verifiedBy;
    }
    
    public void setVerifiedBy(User verifiedBy) {
        this.verifiedBy = verifiedBy;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public LocalDate getCompletionDate() {
        return completionDate;
    }
    
    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
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

