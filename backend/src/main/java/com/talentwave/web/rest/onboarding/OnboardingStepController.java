package com.talentwave.web.rest.onboarding;

import com.talentwave.service.onboarding.OnboardingStepService;
import com.talentwave.service.dto.onboarding.OnboardingStepDTO;
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
@RequestMapping("/api/onboarding/steps")
public class OnboardingStepController {

    private final Logger log = LoggerFactory.getLogger(OnboardingStepController.class);

    private final OnboardingStepService onboardingStepService;

    public OnboardingStepController(OnboardingStepService onboardingStepService) {
        this.onboardingStepService = onboardingStepService;
    }

    /**
     * {@code POST  /api/onboarding/steps} : Create a new onboardingStep.
     *
     * @param onboardingStepDTO the onboardingStepDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new onboardingStepDTO, or with status {@code 400 (Bad Request)} if the onboardingStep has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<OnboardingStepDTO> createOnboardingStep(@Valid @RequestBody OnboardingStepDTO onboardingStepDTO) {
        log.debug("REST request to save OnboardingStep : {}", onboardingStepDTO);
        if (onboardingStepDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        OnboardingStepDTO result = onboardingStepService.save(onboardingStepDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/onboarding/steps/:id} : Updates an existing onboardingStep.
     *
     * @param id the id of the onboardingStepDTO to save.
     * @param onboardingStepDTO the onboardingStepDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onboardingStepDTO,
     * or with status {@code 400 (Bad Request)} if the onboardingStepDTO is not valid,
     * or with status {@code 404 (Not Found)} if the onboardingStepDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the onboardingStepDTO couldn\"t be updated.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<OnboardingStepDTO> updateOnboardingStep(@PathVariable Long id, @Valid @RequestBody OnboardingStepDTO onboardingStepDTO) {
        log.debug("REST request to update OnboardingStep : {}, {}", id, onboardingStepDTO);
        if (onboardingStepDTO.getId() == null || !onboardingStepDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<OnboardingStepDTO> existingStep = onboardingStepService.findOne(id);
        if (existingStep.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        OnboardingStepDTO result = onboardingStepService.save(onboardingStepDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/onboarding/steps/:id} : Partially updates an existing onboardingStep.
     *
     * @param id the id of the onboardingStepDTO to save.
     * @param onboardingStepDTO the onboardingStepDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated onboardingStepDTO,
     * or with status {@code 400 (Bad Request)} if the onboardingStepDTO is not valid,
     * or with status {@code 404 (Not Found)} if the onboardingStepDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the onboardingStepDTO couldn\"t be updated.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<OnboardingStepDTO> partialUpdateOnboardingStep(@PathVariable Long id, @RequestBody OnboardingStepDTO onboardingStepDTO) {
        log.debug("REST request to partially update OnboardingStep : {}, {}", id, onboardingStepDTO);
        if (onboardingStepDTO.getId() == null || !onboardingStepDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<OnboardingStepDTO> updatedStep = onboardingStepService.partialUpdate(onboardingStepDTO);
        return updatedStep.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/onboarding/steps} : get all the onboardingSteps.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of onboardingSteps in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or hasAuthority(\'CONSULTANT\")")
    public ResponseEntity<List<OnboardingStepDTO>> getAllOnboardingSteps(Pageable pageable) {
        log.debug("REST request to get a page of OnboardingSteps");
        Page<OnboardingStepDTO> page = onboardingStepService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/onboarding/steps/:id} : get the "id" onboardingStep.
     *
     * @param id the id of the onboardingStepDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the onboardingStepDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or hasAuthority(\'CONSULTANT\")")
    public ResponseEntity<OnboardingStepDTO> getOnboardingStep(@PathVariable Long id) {
        log.debug("REST request to get OnboardingStep : {}", id);
        Optional<OnboardingStepDTO> onboardingStepDTO = onboardingStepService.findOne(id);
        return onboardingStepDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/onboarding/steps/:id} : delete the "id" onboardingStep.
     *
     * @param id the id of the onboardingStepDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<Void> deleteOnboardingStep(@PathVariable Long id) {
        log.debug("REST request to delete OnboardingStep : {}", id);
        onboardingStepService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

