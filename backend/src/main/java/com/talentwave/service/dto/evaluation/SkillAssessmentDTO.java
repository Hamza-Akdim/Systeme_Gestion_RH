package com.talentwave.service.dto.evaluation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class SkillAssessmentDTO {
    private Long id;

    @NotNull
    private Long consultantId;
    private String consultantName; // Optional: for display

    @NotBlank
    @Size(max = 255)
    private String skillName;

    @NotNull
    @Min(1)
    @Max(5)
    private Integer proficiencyLevel;

    @NotNull
    private LocalDate assessmentDate;

    private Long assessorId; // User ID of the assessor
    private String assessorName; // Optional: for display

    private String comments;

    private Instant createdAt;

    private Instant updatedAt;
    
    // Getters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public String getSkillName() {
        return skillName;
    }
    
    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
    
    public Integer getProficiencyLevel() {
        return proficiencyLevel;
    }
    
    public void setProficiencyLevel(Integer proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }
    
    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }
    
    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
    }
    
    public Long getAssessorId() {
        return assessorId;
    }
    
    public void setAssessorId(Long assessorId) {
        this.assessorId = assessorId;
    }
    
    public String getAssessorName() {
        return assessorName;
    }
    
    public void setAssessorName(String assessorName) {
        this.assessorName = assessorName;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
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

