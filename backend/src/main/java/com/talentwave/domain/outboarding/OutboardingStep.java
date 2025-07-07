package com.talentwave.domain.outboarding;

import com.talentwave.domain.User; // Assuming a User (Consultant) is being offboarded
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "outboarding_step")
@Getter
@Setter
@NoArgsConstructor
public class OutboardingStep {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @Lob
    private String description;

    @NotNull
    private Integer stepOrder;

    private boolean mandatory = true;

    // Link to the consultant being offboarded
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private User consultant;

    private boolean completed = false;

    private LocalDate completionDate;

    @Lob
    private String notes;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    // Could be linked to a generic OutboardingPlan entity if multiple plans exist

    public OutboardingStep(String name, String description, Integer stepOrder, boolean mandatory, User consultant) {
        this.name = name;
        this.description = description;
        this.stepOrder = stepOrder;
        this.mandatory = mandatory;
        this.consultant = consultant;
    }
    
    // Getters et setters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Integer getStepOrder() {
        return stepOrder;
    }
    
    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }
    
    public boolean isMandatory() {
        return mandatory;
    }
    
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
    }
    
    public User getConsultant() {
        return consultant;
    }
    
    public void setConsultant(User consultant) {
        this.consultant = consultant;
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

