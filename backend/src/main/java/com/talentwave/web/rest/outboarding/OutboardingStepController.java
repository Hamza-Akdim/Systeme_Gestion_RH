package com.talentwave.web.rest.outboarding;

import com.talentwave.service.dto.outboarding.OutboardingStepDTO;
import com.talentwave.service.outboarding.OutboardingStepService;
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
@RequestMapping("/api/outboarding/steps")
public class OutboardingStepController {

    private final Logger log = LoggerFactory.getLogger(OutboardingStepController.class);

    private final OutboardingStepService onboardingStepService; // Corrected to OutboardingStepService

    public OutboardingStepController(OutboardingStepService onboardingStepService) {
        this.onboardingStepService = onboardingStepService;
    }

    /**
     * {@code POST  /api/outboarding/steps} : Create a new onboardingStep. // Corrected to outboardingStep
     *
     * @param onboardingStepDTO the onboardingStepDTO to create. // Corrected to outboardingStepDTO
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onboardingStepDTO, or with status {@code 400 (Bad Request)} if the onboardingStep has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<OutboardingStepDTO> createOutboardingStep(@Valid @RequestBody OutboardingStepDTO onboardingStepDTO) {
        log.debug("REST request to save OutboardingStep : {}", onboardingStepDTO);
        if (onboardingStepDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        OutboardingStepDTO result = onboardingStepService.save(onboardingStepDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/outboarding/steps/:id} : Updates an existing onboardingStep. // Corrected to outboardingStep
     *
     * @param id the id of the onboardingStepDTO to save.
     * @param onboardingStepDTO the onboardingStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onboardingStepDTO,
     * or with status {@code 400 (Bad Request)} if the onboardingStepDTO is not valid,
     * or with status {@code 404 (Not Found)} if the onboardingStepDTO is not found.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<OutboardingStepDTO> updateOutboardingStep(@PathVariable Long id, @Valid @RequestBody OutboardingStepDTO onboardingStepDTO) {
        log.debug("REST request to update OutboardingStep : {}, {}", id, onboardingStepDTO);
        if (onboardingStepDTO.getId() == null || !onboardingStepDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<OutboardingStepDTO> existingStep = onboardingStepService.findOne(id);
        if (existingStep.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        OutboardingStepDTO result = onboardingStepService.save(onboardingStepDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/outboarding/steps/:id} : Partially updates an existing onboardingStep. // Corrected to outboardingStep
     *
     * @param id the id of the onboardingStepDTO to save.
     * @param onboardingStepDTO the onboardingStepDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onboardingStepDTO,
     * or with status {@code 400 (Bad Request)} if the onboardingStepDTO is not valid,
     * or with status {@code 404 (Not Found)} if the onboardingStepDTO is not found.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<OutboardingStepDTO> partialUpdateOutboardingStep(@PathVariable Long id, @RequestBody OutboardingStepDTO onboardingStepDTO) {
        log.debug("REST request to partially update OutboardingStep : {}, {}", id, onboardingStepDTO);
        if (onboardingStepDTO.getId() == null || !onboardingStepDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<OutboardingStepDTO> updatedStep = onboardingStepService.partialUpdate(onboardingStepDTO);
        return updatedStep.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/outboarding/steps} : get all the onboardingSteps. // Corrected to outboardingSteps
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onboardingSteps in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or hasAuthority(\'CONSULTANT\")")
    public ResponseEntity<List<OutboardingStepDTO>> getAllOutboardingSteps(Pageable pageable) {
        log.debug("REST request to get a page of OutboardingSteps");
        Page<OutboardingStepDTO> page = onboardingStepService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/outboarding/steps/:id} : get the "id" onboardingStep. // Corrected to outboardingStep
     *
     * @param id the id of the onboardingStepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onboardingStepDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or hasAuthority(\'CONSULTANT\")")
    public ResponseEntity<OutboardingStepDTO> getOutboardingStep(@PathVariable Long id) {
        log.debug("REST request to get OutboardingStep : {}", id);
        Optional<OutboardingStepDTO> onboardingStepDTO = onboardingStepService.findOne(id);
        return onboardingStepDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/outboarding/steps/:id} : delete the "id" onboardingStep. // Corrected to outboardingStep
     *
     * @param id the id of the onboardingStepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<Void> deleteOutboardingStep(@PathVariable Long id) {
        log.debug("REST request to delete OutboardingStep : {}", id);
        onboardingStepService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

