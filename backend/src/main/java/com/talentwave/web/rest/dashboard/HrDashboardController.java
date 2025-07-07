package com.talentwave.web.rest.dashboard;

import com.talentwave.service.dto.dashboard.HrDashboardDTO;
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
public class HrDashboardController {

    private final Logger log = LoggerFactory.getLogger(HrDashboardController.class);

    private final DashboardService dashboardService; // Or HrDashboardService

    public HrDashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    /**
     * {@code GET  /hr} : get HR dashboard data.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the hrDashboardDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/hr")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<HrDashboardDTO> getHrDashboard() {
        log.debug("REST request to get HR Dashboard data");
        HrDashboardDTO hrDashboardDTO = dashboardService.getHrDashboardData(); // Method to be implemented in service
        if (hrDashboardDTO != null) {
            return ResponseEntity.ok(hrDashboardDTO);
        } else {
            // Potentially return an empty DTO or a specific structure if no data is an expected state
            return ResponseEntity.notFound().build(); 
        }
    }
}

