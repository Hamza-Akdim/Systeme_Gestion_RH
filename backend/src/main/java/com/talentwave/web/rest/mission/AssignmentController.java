package com.talentwave.web.rest.mission;

import com.talentwave.service.dto.mission.AssignmentDTO;
import com.talentwave.service.mission.AssignmentService;
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
@RequestMapping("/api/missions/assignments") // Adjusted path to be nested under missions if logical, or /api/assignments if standalone
public class AssignmentController {

    private final Logger log = LoggerFactory.getLogger(AssignmentController.class);

    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    /**
     * {@code POST  /api/missions/assignments} : Create a new assignment.
     *
     * @param assignmentDTO the assignmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assignmentDTO, or with status {@code 400 (Bad Request)} if the assignment has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<AssignmentDTO> createAssignment(@Valid @RequestBody AssignmentDTO assignmentDTO) {
        log.debug("REST request to save Assignment : {}", assignmentDTO);
        if (assignmentDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        AssignmentDTO result = assignmentService.save(assignmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/missions/assignments/:id} : Updates an existing assignment.
     *
     * @param id the id of the assignmentDTO to save.
     * @param assignmentDTO the assignmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assignmentDTO,
     * or with status {@code 400 (Bad Request)} if the assignmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the assignmentDTO is not found.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<AssignmentDTO> updateAssignment(@PathVariable Long id, @Valid @RequestBody AssignmentDTO assignmentDTO) {
        log.debug("REST request to update Assignment : {}, {}", id, assignmentDTO);
        if (assignmentDTO.getId() == null || !assignmentDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<AssignmentDTO> existingAssignment = assignmentService.findOne(id);
        if (existingAssignment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AssignmentDTO result = assignmentService.save(assignmentDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/missions/assignments/:id} : Partially updates an existing assignment.
     *
     * @param id the id of the assignmentDTO to save.
     * @param assignmentDTO the assignmentDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assignmentDTO,
     * or with status {@code 400 (Bad Request)} if the assignmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the assignmentDTO is not found.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<AssignmentDTO> partialUpdateAssignment(@PathVariable Long id, @RequestBody AssignmentDTO assignmentDTO) {
        log.debug("REST request to partially update Assignment : {}, {}", id, assignmentDTO);
        if (assignmentDTO.getId() == null || !assignmentDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<AssignmentDTO> updatedAssignment = assignmentService.partialUpdate(assignmentDTO);
        return updatedAssignment.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/missions/assignments} : get all the assignments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assignments in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or hasAuthority(\'CONSULTANT\")")
    public ResponseEntity<List<AssignmentDTO>> getAllAssignments(Pageable pageable) {
        log.debug("REST request to get a page of Assignments");
        Page<AssignmentDTO> page = assignmentService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/missions/assignments/:id} : get the "id" assignment.
     *
     * @param id the id of the assignmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assignmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or @assignmentService.isRelatedToUser(principal, #id)") // Assuming a method to check if user can access
    public ResponseEntity<AssignmentDTO> getAssignment(@PathVariable Long id) {
        log.debug("REST request to get Assignment : {}", id);
        Optional<AssignmentDTO> assignmentDTO = assignmentService.findOne(id);
        return assignmentDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/missions/assignments/:id} : delete the "id" assignment.
     *
     * @param id the id of the assignmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<Void> deleteAssignment(@PathVariable Long id) {
        log.debug("REST request to delete Assignment : {}", id);
        assignmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

