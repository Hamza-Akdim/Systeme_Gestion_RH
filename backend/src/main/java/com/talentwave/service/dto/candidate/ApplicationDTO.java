package com.talentwave.service.dto.candidate;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * DTO pour les candidatures.
 */
@Data
@NoArgsConstructor
public class ApplicationDTO {
    private Long id;
    private Long candidateId;
    private String candidateName;
    private Long jobOfferId;
    private String jobOfferTitle;
    private String company;
    private String status;
    private LocalDate applicationDate;
    private String notes;
    private Instant createdAt;
    private Instant updatedAt;
    private String coverLetter;
    private String resumeUrl;
    private Integer matchScore;
    private String feedback;
    
    // Getters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getCandidateId() {
        return candidateId;
    }
    
    public void setCandidateId(Long candidateId) {
        this.candidateId = candidateId;
    }
    
    public String getCandidateName() {
        return candidateName;
    }
    
    public void setCandidateName(String candidateName) {
        this.candidateName = candidateName;
    }
    
    public Long getJobOfferId() {
        return jobOfferId;
    }
    
    public void setJobOfferId(Long jobOfferId) {
        this.jobOfferId = jobOfferId;
    }
    
    public String getJobOfferTitle() {
        return jobOfferTitle;
    }
    
    public void setJobOfferTitle(String jobOfferTitle) {
        this.jobOfferTitle = jobOfferTitle;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
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
