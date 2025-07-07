package com.talentwave.domain.mission;

import com.talentwave.domain.User; // Assuming a User (Consultant) is assigned to the mission
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "mission")
@Getter
@Setter
@NoArgsConstructor
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String title;

    @Lob
    private String description;

    @NotBlank
    @Size(max = 255)
    private String clientName;
    
    // Ajout du champ clientId pour correspondre aux appels dans le service
    private Long clientId;

    @NotNull
    private LocalDate startDate;

    @FutureOrPresent
    private LocalDate endDate; // Can be null for ongoing missions

    @NotNull
    @Enumerated(EnumType.STRING)
    private MissionStatus status = MissionStatus.PLANNED;

    // Consider adding fields like dailyRate, projectManager (User), etc.
    private BigDecimal dailyRate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_manager_id")
    private User projectManager; // User responsible for managing this mission

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    public enum MissionStatus {
        PLANNED,
        IN_PROGRESS,
        COMPLETED,
        ON_HOLD,
        CANCELLED
    }

    public Mission(String title, String description, String clientName, LocalDate startDate, User projectManager) {
        this.title = title;
        this.description = description;
        this.clientName = clientName;
        this.startDate = startDate;
        this.projectManager = projectManager;
    }
    
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
    
    public String getClientName() {
        return clientName;
    }
    
    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
    
    public Long getClientId() {
        return clientId;
    }
    
    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }
    
    public LocalDate getEndDate() {
        return endDate;
    }
    
    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
    
    public MissionStatus getStatus() {
        return status;
    }
    
    public void setStatus(MissionStatus status) {
        this.status = status;
    }
    
    public BigDecimal getDailyRate() {
        return dailyRate;
    }
    
    public void setDailyRate(BigDecimal dailyRate) {
        this.dailyRate = dailyRate;
    }
    
    public User getProjectManager() {
        return projectManager;
    }
    
    public void setProjectManager(User projectManager) {
        this.projectManager = projectManager;
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

