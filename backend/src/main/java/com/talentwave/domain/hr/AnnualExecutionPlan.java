package com.talentwave.domain.hr;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "annual_execution_plan")
@Getter
@Setter
@NoArgsConstructor
public class AnnualExecutionPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private Integer year;

    @NotNull
    @Min(0)
    private Integer recruitmentTarget;

    @Lob
    private String objectives;

    // Consider relationships to JobProfile or Department if plans are specific

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    public AnnualExecutionPlan(Integer year, Integer recruitmentTarget, String objectives) {
        this.year = year;
        this.recruitmentTarget = recruitmentTarget;
        this.objectives = objectives;
    }
    
    // Getters et setters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Integer getYear() {
        return year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
    
    public Integer getRecruitmentTarget() {
        return recruitmentTarget;
    }
    
    public void setRecruitmentTarget(Integer recruitmentTarget) {
        this.recruitmentTarget = recruitmentTarget;
    }
    
    public String getObjectives() {
        return objectives;
    }
    
    public void setObjectives(String objectives) {
        this.objectives = objectives;
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

