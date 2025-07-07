package com.talentwave.service.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO pour les données du tableau de bord consultant.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConsultantDashboardDTO {
    
    private Long consultantId;
    private String consultantName;
    
    // Statistiques de missions
    private Long currentMissionId;
    private String currentMissionName;
    private String currentClientName;
    private String currentMissionStartDate;
    private String currentMissionEndDate;
    private Integer daysRemainingInCurrentMission;
    
    // Statistiques de congés
    private Integer totalLeaveDaysAvailable;
    private Integer leaveDaysUsed;
    private Integer leaveDaysRemaining;
    private List<LeaveRequestSummaryDTO> recentLeaveRequests;
    
    // Statistiques de timesheets
    private Integer timesheetsToSubmit;
    private Integer timesheetsSubmitted;
    private Integer timesheetsApproved;
    private Integer timesheetsRejected;
    
    // Statistiques d'évaluation
    private String lastReviewDate;
    private String nextReviewDate;
    private Integer daysUntilNextReview;
    
    // Statistiques de formation
    private Integer completedTrainings;
    private Integer pendingTrainings;
    private Integer upcomingTrainings;
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LeaveRequestSummaryDTO {
        private Long id;
        private String startDate;
        private String endDate;
        private String status;
        private String type;
    }
}
