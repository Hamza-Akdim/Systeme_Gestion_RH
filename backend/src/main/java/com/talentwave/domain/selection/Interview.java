package com.talentwave.domain.selection;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * Entité représentant un entretien dans le processus de sélection.
 */
@Entity
@Table(name = "interviews")
@Getter
@Setter
@NoArgsConstructor
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "application_id")
    private Long applicationId;

    @Column(name = "consultant_id")
    private Long consultantId;

    @Column(name = "scheduled_date_time")
    private LocalDateTime scheduledDateTime;

    @Column(name = "duration_minutes")
    private Integer durationMinutes;

    @Column(name = "status")
    private String status;

    @Column(name = "type")
    private String type;

    @Column(name = "location")
    private String location;

    @Column(name = "meeting_link")
    private String meetingLink;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    /**
     * Constructeur avec paramètres.
     *
     * @param applicationId l'identifiant de la candidature
     * @param consultantId l'identifiant du consultant
     * @param scheduledDateTime la date et l'heure prévues
     * @param durationMinutes la durée en minutes
     * @param type le type d'entretien
     */
    public Interview(Long applicationId, Long consultantId, LocalDateTime scheduledDateTime, Integer durationMinutes, String type) {
        this.applicationId = applicationId;
        this.consultantId = consultantId;
        this.scheduledDateTime = scheduledDateTime;
        this.durationMinutes = durationMinutes;
        this.type = type;
        this.status = "SCHEDULED";
    }
    
    // Getters et setters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getApplicationId() {
        return applicationId;
    }
    
    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }
    
    public Long getConsultantId() {
        return consultantId;
    }
    
    public void setConsultantId(Long consultantId) {
        this.consultantId = consultantId;
    }
    
    public LocalDateTime getScheduledDateTime() {
        return scheduledDateTime;
    }
    
    public void setScheduledDateTime(LocalDateTime scheduledDateTime) {
        this.scheduledDateTime = scheduledDateTime;
    }
    
    public Integer getDurationMinutes() {
        return durationMinutes;
    }
    
    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    public String getLocation() {
        return location;
    }
    
    public void setLocation(String location) {
        this.location = location;
    }
    
    public String getMeetingLink() {
        return meetingLink;
    }
    
    public void setMeetingLink(String meetingLink) {
        this.meetingLink = meetingLink;
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
