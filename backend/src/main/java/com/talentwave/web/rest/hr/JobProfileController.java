package com.talentwave.web.rest.hr;

import com.talentwave.service.hr.JobProfileService;
import com.talentwave.service.dto.hr.JobProfileDTO;
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
@RequestMapping("/api/hr/job-profiles")
public class JobProfileController {

    private final Logger log = LoggerFactory.getLogger(JobProfileController.class);

    private final JobProfileService jobProfileService;

    public JobProfileController(JobProfileService jobProfileService) {
        this.jobProfileService = jobProfileService;
    }

    /**
     * {@code POST  /api/hr/job-profiles} : Create a new jobProfile.
     *
     * @param jobProfileDTO the jobProfileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobProfileDTO, or with status {@code 400 (Bad Request)} if the jobProfile has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<JobProfileDTO> createJobProfile(@Valid @RequestBody JobProfileDTO jobProfileDTO) {
        log.debug("REST request to save JobProfile : {}", jobProfileDTO);
        if (jobProfileDTO.getId() != null) {
            // Consider throwing a BadRequestAlertException or similar
            return ResponseEntity.badRequest().build(); 
        }
        JobProfileDTO result = jobProfileService.save(jobProfileDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/hr/job-profiles/:id} : Updates an existing jobProfile.
     *
     * @param id the id of the jobProfileDTO to save.
     * @param jobProfileDTO the jobProfileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobProfileDTO,
     * or with status {@code 400 (Bad Request)} if the jobProfileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the jobProfileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobProfileDTO couldn't be updated.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<JobProfileDTO> updateJobProfile(@PathVariable Long id, @Valid @RequestBody JobProfileDTO jobProfileDTO) {
        log.debug("REST request to update JobProfile : {}, {}", id, jobProfileDTO);
        if (jobProfileDTO.getId() == null || !jobProfileDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<JobProfileDTO> existingJobProfile = jobProfileService.findOne(id);
        if (existingJobProfile.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        JobProfileDTO result = jobProfileService.save(jobProfileDTO); // save handles create or update
        return ResponseEntity.ok().body(result);
    }
    
    /**
     * {@code PATCH  /api/hr/job-profiles/:id} : Partially updates an existing jobProfile.
     *
     * @param id the id of the jobProfileDTO to save.
     * @param jobProfileDTO the jobProfileDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobProfileDTO,
     * or with status {@code 400 (Bad Request)} if the jobProfileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the jobProfileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobProfileDTO couldn't be updated.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<JobProfileDTO> partialUpdateJobProfile(@PathVariable Long id, @RequestBody JobProfileDTO jobProfileDTO) {
        log.debug("REST request to partially update JobProfile : {}, {}", id, jobProfileDTO);
        if (jobProfileDTO.getId() == null || !jobProfileDTO.getId().equals(id)) {
             return ResponseEntity.badRequest().build();
        }
        Optional<JobProfileDTO> updatedJobProfile = jobProfileService.partialUpdate(jobProfileDTO);
        return updatedJobProfile.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/hr/job-profiles} : get all the jobProfiles.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobProfiles in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or hasAuthority(\'CONSULTANT\')")
    public ResponseEntity<List<JobProfileDTO>> getAllJobProfiles(Pageable pageable) {
        log.debug("REST request to get a page of JobProfiles");
        Page<JobProfileDTO> page = jobProfileService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/hr/job-profiles/:id} : get the "id" jobProfile.
     *
     * @param id the id of the jobProfileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobProfileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or hasAuthority(\'CONSULTANT\')")
    public ResponseEntity<JobProfileDTO> getJobProfile(@PathVariable Long id) {
        log.debug("REST request to get JobProfile : {}", id);
        Optional<JobProfileDTO> jobProfileDTO = jobProfileService.findOne(id);
        return jobProfileDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/hr/job-profiles/:id} : delete the "id" jobProfile.
     *
     * @param id the id of the jobProfileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<Void> deleteJobProfile(@PathVariable Long id) {
        log.debug("REST request to delete JobProfile : {}", id);
        jobProfileService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

