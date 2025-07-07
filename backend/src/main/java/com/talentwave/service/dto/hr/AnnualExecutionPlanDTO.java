package com.talentwave.service.dto.hr;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class AnnualExecutionPlanDTO {
    private Long id;

    @NotNull
    private Integer year;

    @NotNull
    @Min(0)
    private Integer recruitmentTarget;

    private String objectives;

    private Instant createdAt;

    private Instant updatedAt;
    
    // Getters explicites pour résoudre les problèmes de compilation
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

