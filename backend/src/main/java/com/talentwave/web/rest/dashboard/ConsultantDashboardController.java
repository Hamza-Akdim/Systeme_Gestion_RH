package com.talentwave.web.rest.dashboard;

import com.talentwave.service.dto.dashboard.ConsultantDashboardDTO;
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
public class ConsultantDashboardController {

    private final Logger log = LoggerFactory.getLogger(ConsultantDashboardController.class);

    private final DashboardService dashboardService; // Or ConsultantDashboardService

    public ConsultantDashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * {@code GET  /consultant} : get Consultant dashboard data.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the consultantDashboardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/consultant")
    @PreAuthorize("hasAuthority(\'CONSULTANT\') or hasAuthority(\'ADMIN\")") // Admin can also view consultant dashboard for overview
    public ResponseEntity<ConsultantDashboardDTO> getConsultantDashboard() {
        log.debug("REST request to get Consultant Dashboard data");
        ConsultantDashboardDTO consultantDashboardDTO = dashboardService.getConsultantDashboardData(); // Method to be implemented in service
        if (consultantDashboardDTO != null) {
            return ResponseEntity.ok(consultantDashboardDTO);
        } else {
            // Potentially return an empty DTO or a specific structure if no data is an expected state
            return ResponseEntity.notFound().build(); 
        }
    }
}

