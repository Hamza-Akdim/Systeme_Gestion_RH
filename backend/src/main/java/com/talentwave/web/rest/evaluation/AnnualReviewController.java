package com.talentwave.web.rest.evaluation;

import com.talentwave.service.dto.evaluation.AnnualReviewDTO;
import com.talentwave.service.evaluation.AnnualReviewService;
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
@RequestMapping("/api/evaluations/reviews")
public class AnnualReviewController {

    private final Logger log = LoggerFactory.getLogger(AnnualReviewController.class);

    private final AnnualReviewService annualReviewService;

    public AnnualReviewController(AnnualReviewService annualReviewService) {
        this.annualReviewService = annualReviewService;
    }

    /**
     * {@code POST  /api/evaluations/reviews} : Create a new annualReview.
     *
     * @param annualReviewDTO the annualReviewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new annualReviewDTO, or with status {@code 400 (Bad Request)} if the annualReview has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<AnnualReviewDTO> createAnnualReview(@Valid @RequestBody AnnualReviewDTO annualReviewDTO) {
        log.debug("REST request to save AnnualReview : {}", annualReviewDTO);
        if (annualReviewDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        AnnualReviewDTO result = annualReviewService.save(annualReviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/evaluations/reviews/:id} : Updates an existing annualReview.
     *
     * @param id the id of the annualReviewDTO to save.
     * @param annualReviewDTO the annualReviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annualReviewDTO,
     * or with status {@code 400 (Bad Request)} if the annualReviewDTO is not valid,
     * or with status {@code 404 (Not Found)} if the annualReviewDTO is not found.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<AnnualReviewDTO> updateAnnualReview(@PathVariable Long id, @Valid @RequestBody AnnualReviewDTO annualReviewDTO) {
        log.debug("REST request to update AnnualReview : {}, {}", id, annualReviewDTO);
        if (annualReviewDTO.getId() == null || !annualReviewDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<AnnualReviewDTO> existingReview = annualReviewService.findOne(id);
        if (existingReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        AnnualReviewDTO result = annualReviewService.save(annualReviewDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/evaluations/reviews/:id} : Partially updates an existing annualReview.
     *
     * @param id the id of the annualReviewDTO to save.
     * @param annualReviewDTO the annualReviewDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated annualReviewDTO,
     * or with status {@code 400 (Bad Request)} if the annualReviewDTO is not valid,
     * or with status {@code 404 (Not Found)} if the annualReviewDTO is not found.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<AnnualReviewDTO> partialUpdateAnnualReview(@PathVariable Long id, @RequestBody AnnualReviewDTO annualReviewDTO) {
        log.debug("REST request to partially update AnnualReview : {}, {}", id, annualReviewDTO);
        if (annualReviewDTO.getId() == null || !annualReviewDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<AnnualReviewDTO> updatedReview = annualReviewService.partialUpdate(annualReviewDTO);
        return updatedReview.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/evaluations/reviews} : get all the annualReviews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of annualReviews in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or hasAuthority(\'CONSULTANT\")")
    public ResponseEntity<List<AnnualReviewDTO>> getAllAnnualReviews(Pageable pageable) {
        log.debug("REST request to get a page of AnnualReviews");
        Page<AnnualReviewDTO> page = annualReviewService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/evaluations/reviews/:id} : get the "id" annualReview.
     *
     * @param id the id of the annualReviewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the annualReviewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or @annualReviewService.isOwnerOrRelated(principal, #id)") // Assuming a method to check if user can access
    public ResponseEntity<AnnualReviewDTO> getAnnualReview(@PathVariable Long id) {
        log.debug("REST request to get AnnualReview : {}", id);
        Optional<AnnualReviewDTO> annualReviewDTO = annualReviewService.findOne(id);
        return annualReviewDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/evaluations/reviews/:id} : delete the "id" annualReview.
     *
     * @param id the id of the annualReviewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<Void> deleteAnnualReview(@PathVariable Long id) {
        log.debug("REST request to delete AnnualReview : {}", id);
        annualReviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

