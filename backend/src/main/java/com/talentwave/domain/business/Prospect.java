package com.talentwave.domain.business;

import com.talentwave.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Entité représentant un prospect commercial.
 */
@Entity
@Table(name = "prospects")
@Getter
@Setter
@NoArgsConstructor
public class Prospect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "company_name", nullable = false)
    private String companyName;

    @Column(name = "industry")
    private String industry;

    @Column(name = "contact_person")
    private String contactPerson;

    @Column(name = "contact_email")
    private String contactEmail;

    @Column(name = "contact_phone")
    private String contactPhone;

    @Column(name = "website")
    private String website;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "status")
    private String status;

    @Column(name = "source")
    private String source;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "first_contact_date")
    private LocalDate firstContactDate;
    
    @Column(name = "last_contact_date")
    private LocalDate lastContactDate;
    
    @ManyToOne
    @JoinColumn(name = "responsible_user_id")
    private User responsibleUser;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;
    
    // Enum pour le statut du prospect
    public enum ProspectStatus {
        NEW, CONTACTED, QUALIFIED, NEGOTIATION, CLOSED_WON, CLOSED_LOST
    }

    /**
     * Constructeur avec paramètres essentiels.
     *
     * @param companyName le nom de l'entreprise
     * @param industry le secteur d'activité
     * @param contactPerson la personne de contact
     * @param contactEmail l'email de contact
     */
    public Prospect(String companyName, String industry, String contactPerson, String contactEmail) {
        this.companyName = companyName;
        this.industry = industry;
        this.contactPerson = contactPerson;
        this.contactEmail = contactEmail;
        this.status = "NEW";
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
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
    
    public String getContactPerson() {
        return contactPerson;
    }
    
    public String getContactEmail() {
        return contactEmail;
    }
    
    public String getContactPhone() {
        return contactPhone;
    }
    
    public String getWebsite() {
        return website;
    }
    
    public String getAddress() {
        return address;
    }
    
    public String getCity() {
        return city;
    }
    
    public String getCountry() {
        return country;
    }
    
    public String getStatus() {
        return status;
    }
    
    public String getSource() {
        return source;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public LocalDate getFirstContactDate() {
        return firstContactDate;
    }
    
    public LocalDate getLastContactDate() {
        return lastContactDate;
    }
    
    public User getResponsibleUser() {
        return responsibleUser;
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
    
    public void setIndustry(String industry) {
        this.industry = industry;
    }
    
    public void setContactPerson(String contactPerson) {
        this.contactPerson = contactPerson;
    }
    
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }
    
    public void setWebsite(String website) {
        this.website = website;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public void setCity(String city) {
        this.city = city;
    }
    
    public void setCountry(String country) {
        this.country = country;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public void setFirstContactDate(LocalDate firstContactDate) {
        this.firstContactDate = firstContactDate;
    }
    
    public void setLastContactDate(LocalDate lastContactDate) {
        this.lastContactDate = lastContactDate;
    }
    
    public void setResponsibleUser(User responsibleUser) {
        this.responsibleUser = responsibleUser;
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // Méthodes pour la compatibilité avec les services
    public String getContactName() {
        return this.contactPerson;
    }
    
    public void setContactName(String contactName) {
        this.contactPerson = contactName;
    }
    
    public String getEmail() {
        return this.contactEmail;
    }
    
    public void setEmail(String email) {
        this.contactEmail = email;
    }
    
    public String getPhoneNumber() {
        return this.contactPhone;
    }
    
    public void setPhoneNumber(String phoneNumber) {
        this.contactPhone = phoneNumber;
    }
}
