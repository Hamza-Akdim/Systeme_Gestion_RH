package com.talentwave.domain.mission;

import com.talentwave.domain.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "assignment")
@Getter
@Setter
@NoArgsConstructor
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id")
    private Mission mission;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private User consultant;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate; // Can be null for ongoing assignments

    @Enumerated(EnumType.STRING)
    private AssignmentStatus status = AssignmentStatus.ASSIGNED; // Default status
    
    // Enum pour AssignmentStatus
    public enum AssignmentStatus {
        ASSIGNED,
        ACTIVE,
        COMPLETED,
        TERMINATED
    }

    private String roleInMission; // e.g., "Developer", "Project Manager", "Analyst"

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    public Assignment(Mission mission, User consultant, LocalDate startDate, String roleInMission) {
        this.mission = mission;
        this.consultant = consultant;
        this.startDate = startDate;
        this.roleInMission = roleInMission;
        this.status = AssignmentStatus.ASSIGNED;
    }
    
    // Getters et setters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Mission getMission() {
        return mission;
    }
    
    public void setMission(Mission mission) {
        this.mission = mission;
    }
    
    public User getConsultant() {
        return consultant;
    }
    
    public void setConsultant(User consultant) {
        this.consultant = consultant;
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
    
    public AssignmentStatus getStatus() {
        return status;
    }
    
    public void setStatus(AssignmentStatus status) {
        this.status = status;
    }
    
    public void setStatus(String status) {
        try {
            this.status = AssignmentStatus.valueOf(status);
        } catch (IllegalArgumentException e) {
            // Fallback to default status if invalid
            this.status = AssignmentStatus.ASSIGNED;
        }
    }
    
    public String getRoleInMission() {
        return roleInMission;
    }
    
    public void setRoleInMission(String roleInMission) {
        this.roleInMission = roleInMission;
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
