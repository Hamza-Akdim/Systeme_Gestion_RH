package com.talentwave.service.dto.sourcing;

import com.talentwave.domain.sourcing.JobOffer.OfferStatus;
import jakarta.validation.constraints.FutureOrPresent;
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
public class JobOfferDTO {
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String title;

    private String description;

    @NotBlank
    @Size(max = 100)
    private String location;

    @NotNull
    private OfferStatus status;

    @NotNull
    @FutureOrPresent
    private LocalDate closingDate;

    private Long jobProfileId; // To link to JobProfile
    private String jobProfileTitle; // Optional: to display job profile title directly

    private Instant createdAt;

    private Instant updatedAt;
    
    // Getters et setters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public OfferStatus getStatus() {
        return status;
    }
    
    public void setStatus(OfferStatus status) {
        this.status = status;
    }
    
    public LocalDate getClosingDate() {
        return closingDate;
    }
    
    public void setClosingDate(LocalDate closingDate) {
        this.closingDate = closingDate;
    }
    
    public Long getJobProfileId() {
        return jobProfileId;
    }
    
    public void setJobProfileId(Long jobProfileId) {
        this.jobProfileId = jobProfileId;
    }
    
    public String getJobProfileTitle() {
        return jobProfileTitle;
    }
    
    public void setJobProfileTitle(String jobProfileTitle) {
        this.jobProfileTitle = jobProfileTitle;
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

