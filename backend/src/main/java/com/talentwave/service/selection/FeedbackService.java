package com.talentwave.service.selection;

import com.talentwave.service.dto.selection.FeedbackDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface FeedbackService {

    /**
     * Save a feedback.
     *
     * @param feedbackDTO the entity to save.
     * @return the persisted entity.
     */
    FeedbackDTO save(FeedbackDTO feedbackDTO);

    /**
     * Partially updates a feedback.
     *
     * @param feedbackDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FeedbackDTO> partialUpdate(FeedbackDTO feedbackDTO);

    /**
     * Get all the feedbacks.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FeedbackDTO> findAll(Pageable pageable);

    /**
     * Get all the feedbacks for a specific interview.
     *
     * @param interviewId the id of the interview.
     * @return the list of entities.
     */
    List<FeedbackDTO> findAllByInterviewId(Long interviewId);

    /**
     * Get all the feedbacks by a specific reviewer.
     *
     * @param reviewerId the id of the reviewer (User).
     * @return the list of entities.
     */
    List<FeedbackDTO> findAllByReviewerId(Long reviewerId);

    /**
     * Get the "id" feedback.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FeedbackDTO> findOne(Long id);

    /**
     * Delete the "id" feedback.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

