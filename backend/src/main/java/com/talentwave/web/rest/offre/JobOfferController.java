package com.talentwave.web.rest.offre;

import com.talentwave.service.offre.JobOfferService;
import com.talentwave.service.dto.offre.JobOfferDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/job-offers")
public class JobOfferController {

    @Autowired
    private JobOfferService jobOfferService;

    /**
     * {@code POST  /api/job-offers} : Create a new jobOffer.
     *
     * @param jobOfferDTO the jobOfferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new jobOfferDTO, or with status {@code 400 (Bad Request)} if the jobOffer has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<JobOfferDTO> createJobOffer(@Valid @RequestBody JobOfferDTO jobOfferDTO) {
        JobOfferDTO result = jobOfferService.save(jobOfferDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/sourcing/offers/:id} : Updates an existing jobOffer.
     *
     * @param id the id of the jobOfferDTO to save.
     * @param jobOfferDTO the jobOfferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobOfferDTO,
     * or with status {@code 400 (Bad Request)} if the jobOfferDTO is not valid,
     * or with status {@code 404 (Not Found)} if the jobOfferDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobOfferDTO couldn\'t be updated.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<JobOfferDTO> updateJobOffer(@PathVariable Long id, @Valid @RequestBody JobOfferDTO jobOfferDTO) {
        if (jobOfferDTO.getId() == null || !jobOfferDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<JobOfferDTO> existingOffer = jobOfferService.findOne(id);
        if (existingOffer.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        JobOfferDTO result = jobOfferService.save(jobOfferDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/sourcing/offers/:id} : Partially updates an existing jobOffer.
     *
     * @param id the id of the jobOfferDTO to save.
     * @param jobOfferDTO the jobOfferDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated jobOfferDTO,
     * or with status {@code 400 (Bad Request)} if the jobOfferDTO is not valid,
     * or with status {@code 404 (Not Found)} if the jobOfferDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the jobOfferDTO couldn\'t be updated.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<JobOfferDTO> partialUpdateJobOffer(@PathVariable Long id, @RequestBody JobOfferDTO jobOfferDTO) {
        if (jobOfferDTO.getId() == null || !jobOfferDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<JobOfferDTO> updatedOffer = jobOfferService.partialUpdate(jobOfferDTO);
        return updatedOffer.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/sourcing/offers} : get all the jobOffers.
     *
     * @param page
     * @param size
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of jobOffers in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or hasAuthority(\'CONSULTANT\')")
    public ResponseEntity<List<JobOfferDTO>> getAllJobOffers(@RequestParam(defaultValue = "0") int page,
                                                             @RequestParam(defaultValue = "10") int size) {
        Page<JobOfferDTO> jobOfferDTOPage = jobOfferService.findAll(page, size);
        return ResponseEntity.ok().body(jobOfferDTOPage.getContent());
    }

    /**
     * {@code GET  /api/sourcing/offers/:id} : get the "id" jobOffer.
     *
     * @param id the id of the jobOfferDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the jobOfferDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or hasAuthority(\'CONSULTANT\')")
    public ResponseEntity<JobOfferDTO> getJobOffer(@PathVariable Long id) {
        Optional<JobOfferDTO> jobOfferDTO = jobOfferService.findOne(id);
        return jobOfferDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/sourcing/offers/:id} : delete the "id" jobOffer.
     *
     * @param id the id of the jobOfferDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<Void> deleteJobOffer(@PathVariable Long id) {
        jobOfferService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

