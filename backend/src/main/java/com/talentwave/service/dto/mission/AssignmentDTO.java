package com.talentwave.service.dto.mission;

import com.talentwave.domain.mission.Assignment.AssignmentStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AssignmentDTO {
    private Long id;

    @NotNull
    private Long missionId;
    private String missionTitle; // Optional: for display

    @NotNull
    private Long consultantId;
    private String consultantName; // Optional: for display

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @NotNull
    private AssignmentStatus status;

    private String roleInMission;

    private Instant createdAt;

    private Instant updatedAt;
    
    // Getters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getMissionId() {
        return missionId;
    }
    
    public void setMissionId(Long missionId) {
        this.missionId = missionId;
    }
    
    public String getMissionTitle() {
        return missionTitle;
    }
    
    public void setMissionTitle(String missionTitle) {
        this.missionTitle = missionTitle;
    }
    
    public Long getConsultantId() {
        return consultantId;
    }
    
    public void setConsultantId(Long consultantId) {
        this.consultantId = consultantId;
    }
    
    public String getConsultantName() {
        return consultantName;
    }
    
    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
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

