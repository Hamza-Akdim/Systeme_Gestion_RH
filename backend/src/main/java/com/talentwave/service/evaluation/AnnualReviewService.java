package com.talentwave.service.evaluation;

import com.talentwave.service.dto.evaluation.AnnualReviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AnnualReviewService {

    /**
     * Save an annualReview.
     *
     * @param annualReviewDTO the entity to save.
     * @return the persisted entity.
     */
    AnnualReviewDTO save(AnnualReviewDTO annualReviewDTO);

    /**
     * Partially updates an annualReview.
     *
     * @param annualReviewDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnnualReviewDTO> partialUpdate(AnnualReviewDTO annualReviewDTO);

    /**
     * Get all the annualReviews.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnnualReviewDTO> findAll(Pageable pageable);

    /**
     * Get all the annualReviews for a specific consultant.
     *
     * @param consultantId the id of the consultant.
     * @return the list of entities.
     */
    List<AnnualReviewDTO> findAllByConsultantId(Long consultantId);

    /**
     * Get the "id" annualReview.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnnualReviewDTO> findOne(Long id);

    /**
     * Delete the "id" annualReview.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

