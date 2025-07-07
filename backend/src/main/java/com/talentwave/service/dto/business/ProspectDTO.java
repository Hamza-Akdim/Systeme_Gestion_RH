package com.talentwave.service.dto.business;

// import com.talentwave.domain.business.Prospect.ProspectStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ProspectDTO {
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String companyName;

    @Size(max = 100)
    private String industry;

    @Size(max = 100)
    private String contactName;

    @Email
    @Size(max = 100)
    private String email;

    @Size(max = 20)
    private String phoneNumber;

    private String address;

    private String status; // Utilisation de String au lieu de ProspectStatus pour la compatibilité

    private LocalDate firstContactDate;

    private LocalDate lastContactDate;

    private String notes;

    private Long responsibleUserId; // User ID of the person responsible for this prospect
    private String responsibleUserName; // Optional: for display

    private Instant createdAt;

    private Instant updatedAt;
    
    // Getters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public String getCompanyName() {
        return companyName;
    }
    
    public String getIndustry() {
        return industry;
    }
    
    public String getContactName() {
        return contactName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPhoneNumber() {
        return phoneNumber;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getStatus() {
        return status;
    }
    
    public LocalDate getFirstContactDate() {
        return firstContactDate;
    }
    
    public LocalDate getLastContactDate() {
        return lastContactDate;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public Long getResponsibleUserId() {
        return responsibleUserId;
    }
    
    public String getResponsibleUserName() {
        return responsibleUserName;
    }
    
    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    
    // Setters explicites pour résoudre les problèmes de compilation
    public void setId(Long id) {
        this.id = id;
    }
    
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    
    public void setContactName(String contactName) {
        this.contactName = contactName;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setFirstContactDate(LocalDate firstContactDate) {
        this.firstContactDate = firstContactDate;
    }
    
    public void setLastContactDate(LocalDate lastContactDate) {
        this.lastContactDate = lastContactDate;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public void setResponsibleUserId(Long responsibleUserId) {
        this.responsibleUserId = responsibleUserId;
    }
    
    public void setResponsibleUserName(String responsibleUserName) {
        this.responsibleUserName = responsibleUserName;
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}

