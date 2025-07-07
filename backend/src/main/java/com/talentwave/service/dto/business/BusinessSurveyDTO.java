package com.talentwave.service.dto.business;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

/**
 * DTO pour les enquêtes commerciales.
 */
@Data
@NoArgsConstructor
public class BusinessSurveyDTO {
    private Long id;
    private Long prospectId;
    private String prospectName;
    private String prospectCompanyName;
    private String subject;
    private String summary;
    private String potentialNeeds;
    private String nextSteps;
    private LocalDate surveyDate;
    private Long conductedById;
    private String conductedByName;
    private Instant createdAt;
    private Instant updatedAt;
    
    // Getters et setters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getProspectId() {
        return prospectId;
    }
    
    public void setProspectId(Long prospectId) {
        this.prospectId = prospectId;
    }
    
    public String getProspectName() {
        return prospectName;
    }
    
    public void setProspectName(String prospectName) {
        this.prospectName = prospectName;
    }
    
    public String getProspectCompanyName() {
        return prospectCompanyName;
    }
    
    public void setProspectCompanyName(String prospectCompanyName) {
        this.prospectCompanyName = prospectCompanyName;
    }
    
    public String getSubject() {
        return subject;
    }
    
    public void setSubject(String subject) {
        this.subject = subject;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public String getPotentialNeeds() {
        return potentialNeeds;
    }
    
    public void setPotentialNeeds(String potentialNeeds) {
        this.potentialNeeds = potentialNeeds;
    }
    
    public String getNextSteps() {
        return nextSteps;
    }
    
    public void setNextSteps(String nextSteps) {
        this.nextSteps = nextSteps;
    }
    
    public LocalDate getSurveyDate() {
        return surveyDate;
    }
    
    public void setSurveyDate(LocalDate surveyDate) {
        this.surveyDate = surveyDate;
    }
    
    public Long getConductedById() {
        return conductedById;
    }
    
    public void setConductedById(Long conductedById) {
        this.conductedById = conductedById;
    }
    
    public String getConductedByName() {
        return conductedByName;
    }
    
    public void setConductedByName(String conductedByName) {
        this.conductedByName = conductedByName;
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
