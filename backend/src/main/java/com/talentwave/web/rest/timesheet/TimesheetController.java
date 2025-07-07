package com.talentwave.web.rest.timesheet;

import com.talentwave.service.dto.timesheet.TimesheetDTO;
import com.talentwave.service.timesheet.TimesheetService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/timesheets") // Corrected endpoint to /api/timesheets as per initial request
public class TimesheetController {

    private final Logger log = LoggerFactory.getLogger(TimesheetController.class);

    private final TimesheetService timesheetService;

    public TimesheetController(TimesheetService timesheetService) {
        this.timesheetService = timesheetService;
    }

    /**
     * {@code POST  /api/timesheets} : Create a new timesheet.
     *
     * @param timesheetDTO the timesheetDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new timesheetDTO, or with status {@code 400 (Bad Request)} if the timesheet has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'CONSULTANT\') or hasAuthority(\'ADMIN\")")
    public ResponseEntity<TimesheetDTO> createTimesheet(@Valid @RequestBody TimesheetDTO timesheetDTO) {
        log.debug("REST request to save Timesheet : {}", timesheetDTO);
        if (timesheetDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        // Add logic to associate timesheet with the currently logged-in consultant if not admin
        TimesheetDTO result = timesheetService.save(timesheetDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/timesheets/:id} : Updates an existing timesheet.
     *
     * @param id the id of the timesheetDTO to save.
     * @param timesheetDTO the timesheetDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timesheetDTO,
     * or with status {@code 400 (Bad Request)} if the timesheetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the timesheetDTO is not found.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or @timesheetService.isOwner(principal, #id)")
    public ResponseEntity<TimesheetDTO> updateTimesheet(@PathVariable Long id, @Valid @RequestBody TimesheetDTO timesheetDTO) {
        log.debug("REST request to update Timesheet : {}, {}", id, timesheetDTO);
        if (timesheetDTO.getId() == null || !timesheetDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<TimesheetDTO> existingTimesheet = timesheetService.findOne(id);
        if (existingTimesheet.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        TimesheetDTO result = timesheetService.save(timesheetDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/timesheets/:id} : Partially updates an existing timesheet.
     *
     * @param id the id of the timesheetDTO to save.
     * @param timesheetDTO the timesheetDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated timesheetDTO,
     * or with status {@code 400 (Bad Request)} if the timesheetDTO is not valid,
     * or with status {@code 404 (Not Found)} if the timesheetDTO is not found.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or @timesheetService.isOwner(principal, #id)")
    public ResponseEntity<TimesheetDTO> partialUpdateTimesheet(@PathVariable Long id, @RequestBody TimesheetDTO timesheetDTO) {
        log.debug("REST request to partially update Timesheet : {}, {}", id, timesheetDTO);
        if (timesheetDTO.getId() == null || !timesheetDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<TimesheetDTO> updatedTimesheet = timesheetService.partialUpdate(timesheetDTO);
        return updatedTimesheet.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/timesheets} : get all the timesheets for current user or all if admin/hr.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of timesheets in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or hasAuthority(\'CONSULTANT\")")
    public ResponseEntity<List<TimesheetDTO>> getAllTimesheets(Pageable pageable) {
        log.debug("REST request to get a page of Timesheets");
        Page<TimesheetDTO> page = timesheetService.findAllForCurrentUser(pageable); // Modified to findAllForCurrentUser
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/timesheets/:id} : get the "id" timesheet.
     *
     * @param id the id of the timesheetDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the timesheetDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or @timesheetService.isOwner(principal, #id)")
    public ResponseEntity<TimesheetDTO> getTimesheet(@PathVariable Long id) {
        log.debug("REST request to get Timesheet : {}", id);
        Optional<TimesheetDTO> timesheetDTO = timesheetService.findOne(id);
        return timesheetDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/timesheets/:id} : delete the "id" timesheet.
     *
     * @param id the id of the timesheetDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or @timesheetService.isOwner(principal, #id)")
    public ResponseEntity<Void> deleteTimesheet(@PathVariable Long id) {
        log.debug("REST request to delete Timesheet : {}", id);
        timesheetService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

