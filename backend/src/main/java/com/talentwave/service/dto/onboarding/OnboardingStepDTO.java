package com.talentwave.service.dto.onboarding;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class OnboardingStepDTO {
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    private String description;

    @NotNull
    private Integer stepOrder;

    private Boolean mandatory = true;

    private Long consultantId; // ID of the consultant being onboarded
    private String consultantName; // Optional: for display

    private Boolean completed = false;
    
    private LocalDate dueDate;

    private LocalDate completionDate;

    private String notes;

    private Instant createdAt;

    private Instant updatedAt;
    
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
    
    public Boolean isMandatory() {
        return mandatory;
    }
    
    public Boolean getMandatory() {
        return mandatory;
    }
    
    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }
    
    public Long getConsultantId() {
        return consultantId;
    }
    
    public void setConsultantId(Long consultantId) {
        this.consultantId = consultantId;
    }
    
    public String getConsultantName() {
        return consultantName;
    }
    
    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }
    
    public Boolean isCompleted() {
        return completed;
    }
    
    public Boolean getCompleted() {
        return completed;
    }
    
    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
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

