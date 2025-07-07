package com.talentwave.domain.business;

import com.talentwave.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Entité représentant une enquête commerciale.
 */
@Entity
@Table(name = "business_surveys")
@Getter
@Setter
@NoArgsConstructor
public class BusinessSurvey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "prospect_id", nullable = false)
    private Prospect prospect;

    @Column(name = "subject")
    private String subject;

    @Column(name = "summary", columnDefinition = "TEXT")
    private String summary;

    @Column(name = "potential_needs", columnDefinition = "TEXT")
    private String potentialNeeds;

    @Column(name = "next_steps", columnDefinition = "TEXT")
    private String nextSteps;

    @Column(name = "survey_date")
    private LocalDate surveyDate;

    @ManyToOne
    @JoinColumn(name = "conducted_by_id")
    private User conductedBy;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    /**
     * Constructeur avec paramètres.
     *
     * @param prospect le prospect concerné
     * @param summary le résumé de l'enquête
     * @param surveyDate la date de l'enquête
     * @param nextSteps les prochaines étapes
     * @param conductedBy l'utilisateur qui a mené l'enquête
     */
    public BusinessSurvey(Prospect prospect, String summary, LocalDate surveyDate, String nextSteps, User conductedBy) {
        this.prospect = prospect;
        this.summary = summary;
        this.surveyDate = surveyDate;
        this.nextSteps = nextSteps;
        this.conductedBy = conductedBy;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    // Getters et setters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }

    public Prospect getProspect() {
        return prospect;
    }
    
    public void setProspect(Prospect prospect) {
        this.prospect = prospect;
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

    public User getConductedBy() {
        return conductedBy;
    }
    
    public void setConductedBy(User conductedBy) {
        this.conductedBy = conductedBy;
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
