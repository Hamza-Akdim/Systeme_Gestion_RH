package com.talentwave.domain.evaluation;

import com.talentwave.domain.User; // Assuming User (Consultant) is being assessed
import jakarta.persistence.*;
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

@Entity
@Table(name = "skill_assessment")
@Getter
@Setter
@NoArgsConstructor
public class SkillAssessment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private User consultant;

    @NotBlank
    @Size(max = 255)
    private String skillName;

    @NotNull
    @Min(1) // Assuming a proficiency level, e.g., 1 (Beginner) to 5 (Expert)
    @Max(5)
    private Integer proficiencyLevel;

    @NotNull
    private LocalDate assessmentDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assessor_id") // User who performed the assessment
    private User assessor;

    @Lob
    private String comments;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    public SkillAssessment(User consultant, String skillName, Integer proficiencyLevel, LocalDate assessmentDate, User assessor) {
        this.consultant = consultant;
        this.skillName = skillName;
        this.proficiencyLevel = proficiencyLevel;
        this.assessmentDate = assessmentDate;
        this.assessor = assessor;
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
    
    public User getAssessor() {
        return assessor;
    }
    
    public void setAssessor(User assessor) {
        this.assessor = assessor;
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

