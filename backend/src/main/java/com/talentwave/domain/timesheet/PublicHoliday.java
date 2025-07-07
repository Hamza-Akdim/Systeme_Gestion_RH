package com.talentwave.domain.timesheet;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Entité représentant un jour férié dans le système.
 */
@Entity
@Table(name = "public_holidays")
@Getter
@Setter
@NoArgsConstructor
public class PublicHoliday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private LocalDate date;

    @Column(name = "country_code", nullable = false)
    private String countryCode;

    @Column(name = "description")
    private String description;
    
    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    /**
     * Constructeur avec paramètres.
     *
     * @param name le nom du jour férié
     * @param date la date du jour férié
     * @param countryCode le code du pays
     * @param description la description du jour férié
     */
    public PublicHoliday(String name, LocalDate date, String countryCode, String description) {
        this.name = name;
        this.date = date;
        this.countryCode = countryCode;
        this.description = description;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    // Getters et setters explicites pour les champs temporels
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
