package com.talentwave.service.impl;

import com.talentwave.service.DashboardService;
import com.talentwave.service.dto.dashboard.AdminDashboardDTO;
import com.talentwave.service.dto.dashboard.ConsultantDashboardDTO;
import com.talentwave.service.dto.dashboard.HrDashboardDTO;
import org.springframework.stereotype.Service;

/**
 * Implémentation du service de tableau de bord.
 */
@Service
public class DashboardServiceImpl implements DashboardService {

    /**
     * Récupère les données du tableau de bord administrateur.
     *
     * @return les données du tableau de bord administrateur
     */
    @Override
    public AdminDashboardDTO getAdminDashboard() {
        // Dans une implémentation réelle, ces données seraient récupérées depuis différents repositories
        return AdminDashboardDTO.builder()
                .totalUsers(250L)
                .activeUsers(200L)
                .totalConsultants(150L)
                .totalClients(30L)
                .totalProjects(45L)
                .activeProjects(25L)
                .totalApplications(120L)
                .pendingApplications(15L)
                .totalInterviews(80L)
                .upcomingInterviews(10L)
                .totalLeaveRequests(90L)
                .pendingLeaveRequests(12L)
                .totalRevenue(1250000.0)
                .monthlyRevenue(125000.0)
                .quarterlyRevenue(375000.0)
                .yearlyRevenue(1250000.0)
                .newHiresThisMonth(5L)
                .newHiresThisQuarter(15L)
                .newHiresThisYear(45L)
                .build();
    }

    /**
     * Récupère les données du tableau de bord RH.
     *
     * @return les données du tableau de bord RH
     */
    @Override
    public HrDashboardDTO getHrDashboard() {
        // Dans une implémentation réelle, ces données seraient récupérées depuis différents repositories
        return HrDashboardDTO.builder()
                .totalEmployees(200L)
                .activeEmployees(180L)
                .totalCandidates(100L)
                .activeCandidates(50L)
                .openPositions(15L)
                .totalApplications(120L)
                .newApplicationsThisWeek(25L)
                .scheduledInterviews(10L)
                .pendingFeedbacks(8L)
                .pendingLeaveRequests(12L)
                .approvedLeaveRequests(70L)
                .totalLeaveRequests(90L)
                .onboardingInProgress(5L)
                .offboardingInProgress(3L)
                .pendingReviews(20L)
                .completedReviewsThisMonth(15L)
                .build();
    }

    /**
     * Récupère les données du tableau de bord consultant.
     *
     * @param consultantId l'identifiant du consultant
     * @return les données du tableau de bord consultant
     */
    @Override
    public ConsultantDashboardDTO getConsultantDashboard(Long consultantId) {
        // Dans une implémentation réelle, ces données seraient récupérées depuis différents repositories
        return ConsultantDashboardDTO.builder()
                .consultantId(consultantId)
                .consultantName("John Doe")
                .currentMissionId(123L)
                .currentMissionName("Projet XYZ")
                .currentClientName("Client ABC")
                .currentMissionStartDate("2025-01-15")
                .currentMissionEndDate("2025-07-15")
                .daysRemainingInCurrentMission(60)
                .totalLeaveDaysAvailable(25)
                .leaveDaysUsed(10)
                .leaveDaysRemaining(15)
                .timesheetsToSubmit(1)
                .timesheetsSubmitted(20)
                .timesheetsApproved(18)
                .timesheetsRejected(2)
                .lastReviewDate("2024-11-15")
                .nextReviewDate("2025-05-15")
                .daysUntilNextReview(0)
                .completedTrainings(5)
                .pendingTrainings(2)
                .upcomingTrainings(1)
                .build();
    }

    /**
     * Méthode pour la compatibilité avec le contrôleur AdminDashboardController.
     *
     * @return les données du tableau de bord administrateur
     */
    public AdminDashboardDTO getAdminDashboardData() {
        return getAdminDashboard();
    }

    /**
     * Méthode pour la compatibilité avec le contrôleur HrDashboardController.
     *
     * @return les données du tableau de bord RH
     */
    public HrDashboardDTO getHrDashboardData() {
        return getHrDashboard();
    }

    /**
     * Méthode pour la compatibilité avec le contrôleur ConsultantDashboardController.
     *
     * @return les données du tableau de bord consultant
     */
    public ConsultantDashboardDTO getConsultantDashboardData() {
        // Utilise un ID par défaut pour la démonstration
        return getConsultantDashboard(1L);
    }
}
