package com.talentwave.web.rest.timesheet;

import com.talentwave.service.dto.timesheet.LeaveRequestDTO;
import com.talentwave.service.timesheet.LeaveRequestService;
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
@RequestMapping("/api/leaves") // Endpoint for LeaveRequest as per initial request
public class LeaveRequestController {

    private final Logger log = LoggerFactory.getLogger(LeaveRequestController.class);

    private final LeaveRequestService leaveRequestService;

    public LeaveRequestController(LeaveRequestService leaveRequestService) {
        this.leaveRequestService = leaveRequestService;
    }

    /**
     * {@code POST  /api/leaves} : Create a new leaveRequest.
     *
     * @param leaveRequestDTO the leaveRequestDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new leaveRequestDTO, or with status {@code 400 (Bad Request)} if the leaveRequest has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'CONSULTANT\") or hasAuthority(\'ADMIN\")")
    public ResponseEntity<LeaveRequestDTO> createLeaveRequest(@Valid @RequestBody LeaveRequestDTO leaveRequestDTO) {
        log.debug("REST request to save LeaveRequest : {}", leaveRequestDTO);
        if (leaveRequestDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        // Add logic to associate leave request with the currently logged-in consultant if not admin
        LeaveRequestDTO result = leaveRequestService.save(leaveRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/leaves/:id} : Updates an existing leaveRequest.
     *
     * @param id the id of the leaveRequestDTO to save.
     * @param leaveRequestDTO the leaveRequestDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveRequestDTO,
     * or with status {@code 400 (Bad Request)} if the leaveRequestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the leaveRequestDTO is not found.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or @leaveRequestService.isOwner(principal, #id)") // HR can manage, owner can update if status allows
    public ResponseEntity<LeaveRequestDTO> updateLeaveRequest(@PathVariable Long id, @Valid @RequestBody LeaveRequestDTO leaveRequestDTO) {
        log.debug("REST request to update LeaveRequest : {}, {}", id, leaveRequestDTO);
        if (leaveRequestDTO.getId() == null || !leaveRequestDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<LeaveRequestDTO> existingLeaveRequest = leaveRequestService.findOne(id);
        if (existingLeaveRequest.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        // Add more specific logic for status changes if needed (e.g. HR approval)
        LeaveRequestDTO result = leaveRequestService.save(leaveRequestDTO);
        return ResponseEntity.ok().body(result);
    }
    
    /**
     * {@code PATCH  /api/leaves/:id} : Partially updates an existing leaveRequest.
     *
     * @param id the id of the leaveRequestDTO to save.
     * @param leaveRequestDTO the leaveRequestDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated leaveRequestDTO,
     * or with status {@code 400 (Bad Request)} if the leaveRequestDTO is not valid,
     * or with status {@code 404 (Not Found)} if the leaveRequestDTO is not found.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or @leaveRequestService.isOwner(principal, #id)")
    public ResponseEntity<LeaveRequestDTO> partialUpdateLeaveRequest(@PathVariable Long id, @RequestBody LeaveRequestDTO leaveRequestDTO) {
        log.debug("REST request to partially update LeaveRequest : {}, {}", id, leaveRequestDTO);
        if (leaveRequestDTO.getId() == null || !leaveRequestDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<LeaveRequestDTO> updatedLeaveRequest = leaveRequestService.partialUpdate(leaveRequestDTO);
        return updatedLeaveRequest.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/leaves} : get all the leaveRequests for current user or all if admin/hr.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of leaveRequests in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or hasAuthority(\'CONSULTANT\")")
    public ResponseEntity<List<LeaveRequestDTO>> getAllLeaveRequests(Pageable pageable) {
        log.debug("REST request to get a page of LeaveRequests");
        Page<LeaveRequestDTO> page = leaveRequestService.findAllForCurrentUserOrAll(pageable); // Modified to findAllForCurrentUserOrAll
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/leaves/:id} : get the "id" leaveRequest.
     *
     * @param id the id of the leaveRequestDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the leaveRequestDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or @leaveRequestService.isOwner(principal, #id)")
    public ResponseEntity<LeaveRequestDTO> getLeaveRequest(@PathVariable Long id) {
        log.debug("REST request to get LeaveRequest : {}", id);
        Optional<LeaveRequestDTO> leaveRequestDTO = leaveRequestService.findOne(id);
        return leaveRequestDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/leaves/:id} : delete the "id" leaveRequest.
     *
     * @param id the id of the leaveRequestDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or @leaveRequestService.isOwnerAndPending(principal, #id)") // Owner can delete only if pending
    public ResponseEntity<Void> deleteLeaveRequest(@PathVariable Long id) {
        log.debug("REST request to delete LeaveRequest : {}", id);
        leaveRequestService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

