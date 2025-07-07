package com.talentwave.web.rest.selection;

import com.talentwave.service.selection.FeedbackService;
import com.talentwave.service.dto.selection.FeedbackDTO;
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
@RequestMapping("/api/selection/feedbacks")
public class FeedbackController {

    private final Logger log = LoggerFactory.getLogger(FeedbackController.class);

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    /**
     * {@code POST  /api/selection/feedbacks} : Create a new feedback.
     *
     * @param feedbackDTO the feedbackDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new feedbackDTO, or with status {@code 400 (Bad Request)} if the feedback has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or hasAuthority(\'CONSULTANT\')") // Consultant can give feedback
    public ResponseEntity<FeedbackDTO> createFeedback(@Valid @RequestBody FeedbackDTO feedbackDTO) {
        log.debug("REST request to save Feedback : {}", feedbackDTO);
        if (feedbackDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        FeedbackDTO result = feedbackService.save(feedbackDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/selection/feedbacks/:id} : Updates an existing feedback.
     *
     * @param id the id of the feedbackDTO to save.
     * @param feedbackDTO the feedbackDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackDTO,
     * or with status {@code 400 (Bad Request)} if the feedbackDTO is not valid,
     * or with status {@code 404 (Not Found)} if the feedbackDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedbackDTO couldn\"t be updated.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or @feedbackService.isOwnerOfFeedback(principal, #id)") // Only owner or admin/hr can update
    public ResponseEntity<FeedbackDTO> updateFeedback(@PathVariable Long id, @Valid @RequestBody FeedbackDTO feedbackDTO) {
        log.debug("REST request to update Feedback : {}, {}", id, feedbackDTO);
        if (feedbackDTO.getId() == null || !feedbackDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<FeedbackDTO> existingFeedback = feedbackService.findOne(id);
        if (existingFeedback.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        FeedbackDTO result = feedbackService.save(feedbackDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/selection/feedbacks/:id} : Partially updates an existing feedback.
     *
     * @param id the id of the feedbackDTO to save.
     * @param feedbackDTO the feedbackDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated feedbackDTO,
     * or with status {@code 400 (Bad Request)} if the feedbackDTO is not valid,
     * or with status {@code 404 (Not Found)} if the feedbackDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the feedbackDTO couldn\"t be updated.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or @feedbackService.isOwnerOfFeedback(principal, #id)")
    public ResponseEntity<FeedbackDTO> partialUpdateFeedback(@PathVariable Long id, @RequestBody FeedbackDTO feedbackDTO) {
        log.debug("REST request to partially update Feedback : {}, {}", id, feedbackDTO);
        if (feedbackDTO.getId() == null || !feedbackDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<FeedbackDTO> updatedFeedback = feedbackService.partialUpdate(feedbackDTO);
        return updatedFeedback.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/selection/feedbacks} : get all the feedbacks.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedbacks in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\")")
    public ResponseEntity<List<FeedbackDTO>> getAllFeedbacks(Pageable pageable) {
        log.debug("REST request to get a page of Feedbacks");
        Page<FeedbackDTO> page = feedbackService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/selection/feedbacks/interview/:interviewId} : get all feedbacks for a specific interview.
     *
     * @param interviewId the id of the interview.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of feedbacks in body.
     */
    @GetMapping("/interview/{interviewId}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or @feedbackService.isParticipantInInterviewFeedback(principal, #interviewId)")
    public ResponseEntity<List<FeedbackDTO>> getFeedbacksByInterview(@PathVariable Long interviewId) {
        log.debug("REST request to get Feedbacks for Interview ID: {}", interviewId);
        List<FeedbackDTO> feedbacks = feedbackService.findAllByInterviewId(interviewId);
        return ResponseEntity.ok().body(feedbacks);
    }

    /**
     * {@code GET  /api/selection/feedbacks/:id} : get the "id" feedback.
     *
     * @param id the id of the feedbackDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the feedbackDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or @feedbackService.isOwnerOfFeedback(principal, #id) or @feedbackService.isFeedbackRelatedToUserInterview(principal, #id)")
    public ResponseEntity<FeedbackDTO> getFeedback(@PathVariable Long id) {
        log.debug("REST request to get Feedback : {}", id);
        Optional<FeedbackDTO> feedbackDTO = feedbackService.findOne(id);
        return feedbackDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/selection/feedbacks/:id} : delete the "id" feedback.
     *
     * @param id the id of the feedbackDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\")")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        log.debug("REST request to delete Feedback : {}", id);
        feedbackService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

