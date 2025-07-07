package com.talentwave.web.rest.evaluation;

import com.talentwave.service.dto.evaluation.SkillAssessmentDTO;
import com.talentwave.service.evaluation.SkillAssessmentService;
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
@RequestMapping("/api/evaluations/assessments")
public class SkillAssessmentController {

    private final Logger log = LoggerFactory.getLogger(SkillAssessmentController.class);

    private final SkillAssessmentService skillAssessmentService;

    public SkillAssessmentController(SkillAssessmentService skillAssessmentService) {
        this.skillAssessmentService = skillAssessmentService;
    }

    /**
     * {@code POST  /api/evaluations/assessments} : Create a new skillAssessment.
     *
     * @param skillAssessmentDTO the skillAssessmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new skillAssessmentDTO, or with status {@code 400 (Bad Request)} if the skillAssessment has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<SkillAssessmentDTO> createSkillAssessment(@Valid @RequestBody SkillAssessmentDTO skillAssessmentDTO) {
        log.debug("REST request to save SkillAssessment : {}", skillAssessmentDTO);
        if (skillAssessmentDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        SkillAssessmentDTO result = skillAssessmentService.save(skillAssessmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/evaluations/assessments/:id} : Updates an existing skillAssessment.
     *
     * @param id the id of the skillAssessmentDTO to save.
     * @param skillAssessmentDTO the skillAssessmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillAssessmentDTO,
     * or with status {@code 400 (Bad Request)} if the skillAssessmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the skillAssessmentDTO is not found.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<SkillAssessmentDTO> updateSkillAssessment(@PathVariable Long id, @Valid @RequestBody SkillAssessmentDTO skillAssessmentDTO) {
        log.debug("REST request to update SkillAssessment : {}, {}", id, skillAssessmentDTO);
        if (skillAssessmentDTO.getId() == null || !skillAssessmentDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<SkillAssessmentDTO> existingAssessment = skillAssessmentService.findOne(id);
        if (existingAssessment.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SkillAssessmentDTO result = skillAssessmentService.save(skillAssessmentDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/evaluations/assessments/:id} : Partially updates an existing skillAssessment.
     *
     * @param id the id of the skillAssessmentDTO to save.
     * @param skillAssessmentDTO the skillAssessmentDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated skillAssessmentDTO,
     * or with status {@code 400 (Bad Request)} if the skillAssessmentDTO is not valid,
     * or with status {@code 404 (Not Found)} if the skillAssessmentDTO is not found.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<SkillAssessmentDTO> partialUpdateSkillAssessment(@PathVariable Long id, @RequestBody SkillAssessmentDTO skillAssessmentDTO) {
        log.debug("REST request to partially update SkillAssessment : {}, {}", id, skillAssessmentDTO);
        if (skillAssessmentDTO.getId() == null || !skillAssessmentDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<SkillAssessmentDTO> updatedAssessment = skillAssessmentService.partialUpdate(skillAssessmentDTO);
        return updatedAssessment.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/evaluations/assessments} : get all the skillAssessments.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of skillAssessments in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or hasAuthority(\'CONSULTANT\')")
    public ResponseEntity<List<SkillAssessmentDTO>> getAllSkillAssessments(Pageable pageable) {
        log.debug("REST request to get a page of SkillAssessments");
        Page<SkillAssessmentDTO> page = skillAssessmentService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/evaluations/assessments/:id} : get the "id" skillAssessment.
     *
     * @param id the id of the skillAssessmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the skillAssessmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or @skillAssessmentService.isOwnerOrRelated(principal, #id)") // Assuming a method to check if user can access
    public ResponseEntity<SkillAssessmentDTO> getSkillAssessment(@PathVariable Long id) {
        log.debug("REST request to get SkillAssessment : {}", id);
        Optional<SkillAssessmentDTO> skillAssessmentDTO = skillAssessmentService.findOne(id);
        return skillAssessmentDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/evaluations/assessments/:id} : delete the "id" skillAssessment.
     *
     * @param id the id of the skillAssessmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<Void> deleteSkillAssessment(@PathVariable Long id) {
        log.debug("REST request to delete SkillAssessment : {}", id);
        skillAssessmentService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

