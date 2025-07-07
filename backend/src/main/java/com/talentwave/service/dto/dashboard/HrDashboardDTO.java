package com.talentwave.service.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour les données du tableau de bord RH.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HrDashboardDTO {
    
    private Long totalEmployees;
    private Long activeEmployees;
    private Long totalCandidates;
    private Long activeCandidates;
    
    // Statistiques de recrutement
    private Long openPositions;
    private Long totalApplications;
    private Long newApplicationsThisWeek;
    private Long scheduledInterviews;
    private Long pendingFeedbacks;
    
    // Statistiques de congés
    private Long pendingLeaveRequests;
    private Long approvedLeaveRequests;
    private Long totalLeaveRequests;
    
    // Statistiques d'onboarding/offboarding
    private Long onboardingInProgress;
    private Long offboardingInProgress;
    
    // Statistiques d'évaluation
    private Long pendingReviews;
    private Long completedReviewsThisMonth;
}
