package com.talentwave.web.rest.hr;

import com.talentwave.service.hr.AnnualExecutionPlanService;
import com.talentwave.service.dto.hr.AnnualExecutionPlanDTO;
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
@RequestMapping("/api/hr/plans")
public class AnnualExecutionPlanController {

    private final Logger log = LoggerFactory.getLogger(AnnualExecutionPlanController.class);

    private final AnnualExecutionPlanService annualExecutionPlanService;

    public AnnualExecutionPlanController(AnnualExecutionPlanService annualExecutionPlanService) {
        this.annualExecutionPlanService = annualExecutionPlanService;
    }

    /**
     * {@code POST  /api/hr/plans} : Create a new annualExecutionPlan.
     *
     * @param annualExecutionPlanDTO the annualExecutionPlanDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new annualExecutionPlanDTO, or with status {@code 400 (Bad Request)} if the annualExecutionPlan has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<AnnualExecutionPlanDTO> createAnnualExecutionPlan(@Valid @RequestBody AnnualExecutionPlanDTO annualExecutionPlanDTO) {
        log.debug("REST request to save AnnualExecutionPlan : {}", annualExecutionPlanDTO);
        if (annualExecutionPlanDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        AnnualExecutionPlanDTO result = annualExecutionPlanService.save(annualExecutionPlanDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/hr/plans/:id} : Updates an existing annualExecutionPlan.
     *
     * @param id the id of the annualExecutionPlanDTO to save.
     * @param annualExecutionPlanDTO the annualExecutionPlanDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annualExecutionPlanDTO,
     * or with status {@code 400 (Bad Request)} if the annualExecutionPlanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the annualExecutionPlanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the annualExecutionPlanDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<AnnualExecutionPlanDTO> updateAnnualExecutionPlan(@PathVariable Long id, @Valid @RequestBody AnnualExecutionPlanDTO annualExecutionPlanDTO) {
        log.debug("REST request to update AnnualExecutionPlan : {}, {}", id, annualExecutionPlanDTO);
        if (annualExecutionPlanDTO.getId() == null || !annualExecutionPlanDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<AnnualExecutionPlanDTO> existingPlan = annualExecutionPlanService.findOne(id);
        if (existingPlan.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AnnualExecutionPlanDTO result = annualExecutionPlanService.save(annualExecutionPlanDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/hr/plans/:id} : Partially updates an existing annualExecutionPlan.
     *
     * @param id the id of the annualExecutionPlanDTO to save.
     * @param annualExecutionPlanDTO the annualExecutionPlanDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annualExecutionPlanDTO,
     * or with status {@code 400 (Bad Request)} if the annualExecutionPlanDTO is not valid,
     * or with status {@code 404 (Not Found)} if the annualExecutionPlanDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the annualExecutionPlanDTO couldn't be updated.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<AnnualExecutionPlanDTO> partialUpdateAnnualExecutionPlan(@PathVariable Long id, @RequestBody AnnualExecutionPlanDTO annualExecutionPlanDTO) {
        log.debug("REST request to partially update AnnualExecutionPlan : {}, {}", id, annualExecutionPlanDTO);
         if (annualExecutionPlanDTO.getId() == null || !annualExecutionPlanDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<AnnualExecutionPlanDTO> updatedPlan = annualExecutionPlanService.partialUpdate(annualExecutionPlanDTO);
        return updatedPlan.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/hr/plans} : get all the annualExecutionPlans.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of annualExecutionPlans in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<List<AnnualExecutionPlanDTO>> getAllAnnualExecutionPlans(Pageable pageable) {
        log.debug("REST request to get a page of AnnualExecutionPlans");
        Page<AnnualExecutionPlanDTO> page = annualExecutionPlanService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/hr/plans/:id} : get the "id" annualExecutionPlan.
     *
     * @param id the id of the annualExecutionPlanDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the annualExecutionPlanDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<AnnualExecutionPlanDTO> getAnnualExecutionPlan(@PathVariable Long id) {
        log.debug("REST request to get AnnualExecutionPlan : {}", id);
        Optional<AnnualExecutionPlanDTO> annualExecutionPlanDTO = annualExecutionPlanService.findOne(id);
        return annualExecutionPlanDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/hr/plans/:id} : delete the "id" annualExecutionPlan.
     *
     * @param id the id of the annualExecutionPlanDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<Void> deleteAnnualExecutionPlan(@PathVariable Long id) {
        log.debug("REST request to delete AnnualExecutionPlan : {}", id);
        annualExecutionPlanService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

