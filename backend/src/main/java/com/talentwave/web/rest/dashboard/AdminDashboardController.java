package com.talentwave.web.rest.dashboard;

import com.talentwave.service.dto.dashboard.AdminDashboardDTO;
import com.talentwave.service.DashboardService; // Assuming a general dashboard service or a specific one
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
public class AdminDashboardController {

    private final Logger log = LoggerFactory.getLogger(AdminDashboardController.class);

    private final DashboardService dashboardService; // Or AdminDashboardService

    public AdminDashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * {@code GET  /admin} : get Admin dashboard data.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the adminDashboardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/admin")
    @PreAuthorize("hasAuthority(\'ADMIN\')")
    public ResponseEntity<AdminDashboardDTO> getAdminDashboard() {
        log.debug("REST request to get Admin Dashboard data");
        AdminDashboardDTO adminDashboardDTO = dashboardService.getAdminDashboardData(); // Method to be implemented in service
        if (adminDashboardDTO != null) {
            return ResponseEntity.ok(adminDashboardDTO);
        } else {
            // Potentially return an empty DTO or a specific structure if no data is an expected state
            return ResponseEntity.notFound().build(); 
        }
    }
}

