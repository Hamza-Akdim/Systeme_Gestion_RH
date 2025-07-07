package com.talentwave.service;

import com.talentwave.service.dto.dashboard.AdminDashboardDTO;
import com.talentwave.service.dto.dashboard.ConsultantDashboardDTO;
import com.talentwave.service.dto.dashboard.HrDashboardDTO;

/**
 * Service Interface for managing dashboard data.
 */
public interface DashboardService {

    /**
     * Get dashboard data for administrators.
     *
     * @return the dashboard data
     */
    AdminDashboardDTO getAdminDashboard();
    
    /**
     * Get dashboard data for administrators.
     * Méthode de compatibilité pour les contrôleurs existants.
     *
     * @return the dashboard data
     */
    AdminDashboardDTO getAdminDashboardData();

    /**
     * Get dashboard data for HR personnel.
     *
     * @return the dashboard data
     */
    HrDashboardDTO getHrDashboard();
    
    /**
     * Get dashboard data for HR personnel.
     * Méthode de compatibilité pour les contrôleurs existants.
     *
     * @return the dashboard data
     */
    HrDashboardDTO getHrDashboardData();

    /**
     * Get dashboard data for consultants.
     *
     * @param consultantId the ID of the consultant
     * @return the dashboard data
     */
    ConsultantDashboardDTO getConsultantDashboard(Long consultantId);
    
    /**
     * Get dashboard data for the current consultant.
     * Méthode de compatibilité pour les contrôleurs existants.
     *
     * @return the dashboard data
     */
    ConsultantDashboardDTO getConsultantDashboardData();
}
