package com.talentwave.web.rest.candidate;

import com.talentwave.service.candidate.ApplicationService;
import com.talentwave.service.dto.candidate.ApplicationDTO;
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
@RequestMapping("/api/applications")
public class ApplicationController {

    private final Logger log = LoggerFactory.getLogger(ApplicationController.class);

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    /**
     * {@code POST  /api/applications} : Create a new application.
     *
     * @param applicationDTO the applicationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new applicationDTO, or with status {@code 400 (Bad Request)} if the application has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or hasAuthority(\'CONSULTANT\')") // Consultant can apply
    public ResponseEntity<ApplicationDTO> createApplication(@Valid @RequestBody ApplicationDTO applicationDTO) {
        log.debug("REST request to save Application : {}", applicationDTO);
        if (applicationDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        ApplicationDTO result = applicationService.save(applicationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/applications/:id} : Updates an existing application.
     *
     * @param id the id of the applicationDTO to save.
     * @param applicationDTO the applicationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationDTO,
     * or with status {@code 400 (Bad Request)} if the applicationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the applicationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicationDTO couldn\"t be updated.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')") // Consultant might only update specific fields or status
    public ResponseEntity<ApplicationDTO> updateApplication(@PathVariable Long id, @Valid @RequestBody ApplicationDTO applicationDTO) {
        log.debug("REST request to update Application : {}, {}", id, applicationDTO);
        if (applicationDTO.getId() == null || !applicationDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<ApplicationDTO> existingApplication = applicationService.findOne(id);
        if (existingApplication.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ApplicationDTO result = applicationService.save(applicationDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/applications/:id} : Partially updates an existing application.
     *
     * @param id the id of the applicationDTO to save.
     * @param applicationDTO the applicationDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated applicationDTO,
     * or with status {@code 400 (Bad Request)} if the applicationDTO is not valid,
     * or with status {@code 404 (Not Found)} if the applicationDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the applicationDTO couldn\"t be updated.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<ApplicationDTO> partialUpdateApplication(@PathVariable Long id, @RequestBody ApplicationDTO applicationDTO) {
        log.debug("REST request to partially update Application : {}, {}", id, applicationDTO);
        if (applicationDTO.getId() == null || !applicationDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<ApplicationDTO> updatedApplication = applicationService.partialUpdate(applicationDTO);
        return updatedApplication.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/applications} : get all the applications.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applications in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<List<ApplicationDTO>> getAllApplications(Pageable pageable) {
        log.debug("REST request to get a page of Applications");
        Page<ApplicationDTO> page = applicationService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }
    
    /**
     * {@code GET  /api/applications/candidate/:candidateId} : get all applications for a specific candidate.
     *
     * @param candidateId the id of the candidate.
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applications in body.
     */
    @GetMapping("/candidate/{candidateId}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or @applicationService.isOwnerOfApplicationByCandidate(principal, #candidateId)")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByCandidate(@PathVariable Long candidateId, Pageable pageable) {
        log.debug("REST request to get Applications for Candidate ID: {}", candidateId);
        Page<ApplicationDTO> page = applicationService.findAllByCandidateId(candidateId, pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/applications/offer/:jobOfferId} : get all applications for a specific job offer.
     *
     * @param jobOfferId the id of the job offer.
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of applications in body.
     */
    @GetMapping("/offer/{jobOfferId}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<List<ApplicationDTO>> getApplicationsByJobOffer(@PathVariable Long jobOfferId, Pageable pageable) {
        log.debug("REST request to get Applications for Job Offer ID: {}", jobOfferId);
        Page<ApplicationDTO> page = applicationService.findAllByJobOfferId(jobOfferId, pageable);
        return ResponseEntity.ok().body(page.getContent());
    }


    /**
     * {@code GET  /api/applications/:id} : get the "id" application.
     *
     * @param id the id of the applicationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the applicationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or @applicationService.isOwnerOfApplication(principal, #id)")
    public ResponseEntity<ApplicationDTO> getApplication(@PathVariable Long id) {
        log.debug("REST request to get Application : {}", id);
        Optional<ApplicationDTO> applicationDTO = applicationService.findOne(id);
        return applicationDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/applications/:id} : delete the "id" application.
     *
     * @param id the id of the applicationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        log.debug("REST request to delete Application : {}", id);
        applicationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

