package com.talentwave.service.dto.dashboard;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO pour les données du tableau de bord administrateur.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminDashboardDTO {
    
    private Long totalUsers;
    private Long activeUsers;
    private Long totalConsultants;
    private Long totalClients;
    private Long totalProjects;
    private Long activeProjects;
    private Long totalApplications;
    private Long pendingApplications;
    private Long totalInterviews;
    private Long upcomingInterviews;
    private Long totalLeaveRequests;
    private Long pendingLeaveRequests;
    
    // Statistiques financières
    private Double totalRevenue;
    private Double monthlyRevenue;
    private Double quarterlyRevenue;
    private Double yearlyRevenue;
    
    // Statistiques de recrutement
    private Long newHiresThisMonth;
    private Long newHiresThisQuarter;
    private Long newHiresThisYear;
}
