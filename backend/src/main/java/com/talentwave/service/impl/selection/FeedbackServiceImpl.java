package com.talentwave.service.impl.selection;

import com.talentwave.domain.User;
import com.talentwave.domain.selection.Feedback;
import com.talentwave.domain.selection.Interview;
import com.talentwave.repository.UserRepository;
import com.talentwave.repository.selection.FeedbackRepository;
import com.talentwave.repository.selection.InterviewRepository;
import com.talentwave.service.selection.FeedbackService;
import com.talentwave.service.dto.selection.FeedbackDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final Logger log = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private final FeedbackRepository feedbackRepository;
    private final InterviewRepository interviewRepository;
    private final UserRepository userRepository;

    public FeedbackServiceImpl(FeedbackRepository feedbackRepository, 
                             InterviewRepository interviewRepository, 
                             UserRepository userRepository) {
        this.feedbackRepository = feedbackRepository;
        this.interviewRepository = interviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public FeedbackDTO save(FeedbackDTO feedbackDTO) {
        log.debug("Request to save Feedback : {}", feedbackDTO);
        Feedback feedback = toEntity(feedbackDTO);
        if (feedback.getId() == null) { // New entity
            feedback.setCreatedAt(Instant.now());
        } else { // Existing entity
            feedback.setUpdatedAt(Instant.now());
            // Créer une variable finale pour utilisation dans la lambda
            final Feedback finalFeedback = feedback;
            feedbackRepository.findById(feedback.getId()).ifPresent(existing -> finalFeedback.setCreatedAt(existing.getCreatedAt()));
        }
        feedback = feedbackRepository.save(feedback);
        return toDto(feedback);
    }

    @Override
    public Optional<FeedbackDTO> partialUpdate(FeedbackDTO feedbackDTO) {
        log.debug("Request to partially update Feedback : {}", feedbackDTO);

        return feedbackRepository
            .findById(feedbackDTO.getId())
            .map(existingFeedback -> {
                if (feedbackDTO.getInterviewId() != null) {
                    existingFeedback.setInterviewId(feedbackDTO.getInterviewId());
                }
                if (feedbackDTO.getReviewerId() != null) {
                    existingFeedback.setReviewerId(feedbackDTO.getReviewerId());
                }
                if (feedbackDTO.getRating() != null) {
                    existingFeedback.setRating(feedbackDTO.getRating());
                }
                if (feedbackDTO.getComments() != null) {
                    existingFeedback.setComments(feedbackDTO.getComments());
                }
                if (feedbackDTO.getPros() != null) {
                    existingFeedback.setPros(feedbackDTO.getPros());
                }
                if (feedbackDTO.getCons() != null) {
                    existingFeedback.setCons(feedbackDTO.getCons());
                }
                if (feedbackDTO.getRecommendation() != null) {
                    existingFeedback.setRecommendation(feedbackDTO.getRecommendation());
                }
                existingFeedback.setUpdatedAt(Instant.now());
                return existingFeedback;
            })
            .map(feedbackRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FeedbackDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Feedbacks");
        return feedbackRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackDTO> findAllByInterviewId(Long interviewId) {
        log.debug("Request to get all Feedbacks for Interview ID: {}", interviewId);
        return feedbackRepository.findByInterviewId(interviewId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<FeedbackDTO> findAllByReviewerId(Long reviewerId) {
        log.debug("Request to get all Feedbacks for Reviewer ID: {}", reviewerId);
        return feedbackRepository.findByReviewerId(reviewerId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FeedbackDTO> findOne(Long id) {
        log.debug("Request to get Feedback : {}", id);
        return feedbackRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Feedback : {}", id);
        feedbackRepository.deleteById(id);
    }

    private FeedbackDTO toDto(Feedback feedback) {
        FeedbackDTO dto = new FeedbackDTO();
        dto.setId(feedback.getId());
        dto.setInterviewId(feedback.getInterviewId());
        dto.setReviewerId(feedback.getReviewerId());
        dto.setReviewerName(feedback.getReviewerName());
        dto.setRating(feedback.getRating());
        dto.setComments(feedback.getComments());
        dto.setPros(feedback.getPros());
        dto.setCons(feedback.getCons());
        dto.setRecommendation(feedback.getRecommendation());
        dto.setCreatedAt(feedback.getCreatedAt());
        dto.setUpdatedAt(feedback.getUpdatedAt());
        return dto;
    }

    private Feedback toEntity(FeedbackDTO dto) {
        // Utiliser le constructeur sans argument pour éviter les erreurs de compilation
        Feedback entity = new Feedback();
        entity.setId(dto.getId());
        entity.setInterviewId(dto.getInterviewId());
        entity.setReviewerId(dto.getReviewerId());
        entity.setRating(dto.getRating());
        entity.setComments(dto.getComments());
        entity.setPros(dto.getPros());
        entity.setCons(dto.getCons());
        entity.setRecommendation(dto.getRecommendation());
        // createdAt and updatedAt are handled in save/update logic
        return entity;
    }
}

