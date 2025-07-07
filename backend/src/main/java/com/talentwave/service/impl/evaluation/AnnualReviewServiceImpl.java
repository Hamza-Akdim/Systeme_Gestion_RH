package com.talentwave.service.impl.evaluation;

import com.talentwave.domain.User;
import com.talentwave.domain.evaluation.AnnualReview;
import java.time.LocalDate;
import com.talentwave.repository.UserRepository;
import com.talentwave.repository.evaluation.AnnualReviewRepository;
import com.talentwave.service.dto.evaluation.AnnualReviewDTO;
import com.talentwave.service.evaluation.AnnualReviewService;
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
public class AnnualReviewServiceImpl implements AnnualReviewService {

    private final Logger log = LoggerFactory.getLogger(AnnualReviewServiceImpl.class);

    private final AnnualReviewRepository annualReviewRepository;
    private final UserRepository userRepository;

    public AnnualReviewServiceImpl(AnnualReviewRepository annualReviewRepository, UserRepository userRepository) {
        this.annualReviewRepository = annualReviewRepository;
        this.userRepository = userRepository;
    }

    @Override
    public AnnualReviewDTO save(AnnualReviewDTO annualReviewDTO) {
        log.debug("Request to save AnnualReview : {}", annualReviewDTO);
        AnnualReview annualReview = toEntity(annualReviewDTO);
        if (annualReview.getId() == null) { // New entity
            annualReview.setCreatedAt(Instant.now());
        } else { // Existing entity
            annualReview.setUpdatedAt(Instant.now());
            // Créer une variable finale pour utilisation dans la lambda
            final AnnualReview finalAnnualReview = annualReview;
            annualReviewRepository.findById(annualReview.getId()).ifPresent(existing -> finalAnnualReview.setCreatedAt(existing.getCreatedAt()));
        }
        annualReview = annualReviewRepository.save(annualReview);
        return toDto(annualReview);
    }

    @Override
    public Optional<AnnualReviewDTO> partialUpdate(AnnualReviewDTO annualReviewDTO) {
        log.debug("Request to partially update AnnualReview : {}", annualReviewDTO);

        return annualReviewRepository
            .findById(annualReviewDTO.getId())
            .map(existingAnnualReview -> {
                if (annualReviewDTO.getConsultantId() != null) {
                    userRepository.findById(annualReviewDTO.getConsultantId()).ifPresent(existingAnnualReview::setConsultant);
                }
                if (annualReviewDTO.getReviewYear() != null) {
                    existingAnnualReview.setReviewYear(annualReviewDTO.getReviewYear());
                }
                if (annualReviewDTO.getReviewDate() != null) {
                    existingAnnualReview.setReviewDate(annualReviewDTO.getReviewDate());
                }
                if (annualReviewDTO.getReviewerId() != null) {
                    userRepository.findById(annualReviewDTO.getReviewerId()).ifPresent(existingAnnualReview::setReviewer);
                }
                if (annualReviewDTO.getOverallPerformanceRating() != null) {
                    existingAnnualReview.setOverallPerformanceRating(annualReviewDTO.getOverallPerformanceRating());
                }
                if (annualReviewDTO.getStrengths() != null) {
                    existingAnnualReview.setStrengths(annualReviewDTO.getStrengths());
                }
                if (annualReviewDTO.getAreasForImprovement() != null) {
                    existingAnnualReview.setAreasForImprovement(annualReviewDTO.getAreasForImprovement());
                }
                if (annualReviewDTO.getGoalsForNextYear() != null) {
                    existingAnnualReview.setGoalsForNextYear(annualReviewDTO.getGoalsForNextYear());
                }
                if (annualReviewDTO.getConsultantComments() != null) {
                    existingAnnualReview.setConsultantComments(annualReviewDTO.getConsultantComments());
                }
                existingAnnualReview.setUpdatedAt(Instant.now());
                return existingAnnualReview;
            })
            .map(annualReviewRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnualReviewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnnualReviews");
        return annualReviewRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AnnualReviewDTO> findAllByConsultantId(Long consultantId) {
        log.debug("Request to get all AnnualReviews for Consultant ID: {}", consultantId);
        return annualReviewRepository.findByConsultantId(consultantId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnnualReviewDTO> findOne(Long id) {
        log.debug("Request to get AnnualReview : {}", id);
        return annualReviewRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnnualReview : {}", id);
        annualReviewRepository.deleteById(id);
    }

    private AnnualReviewDTO toDto(AnnualReview annualReview) {
        AnnualReviewDTO dto = new AnnualReviewDTO();
        dto.setId(annualReview.getId());
        if (annualReview.getConsultant() != null) {
            dto.setConsultantId(annualReview.getConsultant().getId());
            dto.setConsultantName(annualReview.getConsultant().getUsername()); // Or full name
        }
        dto.setReviewYear(annualReview.getReviewYear());
        dto.setReviewDate(annualReview.getReviewDate());
        if (annualReview.getReviewer() != null) {
            dto.setReviewerId(annualReview.getReviewer().getId());
            dto.setReviewerName(annualReview.getReviewer().getUsername()); // Or full name
        }
        dto.setOverallPerformanceRating(annualReview.getOverallPerformanceRating());
        dto.setStrengths(annualReview.getStrengths());
        dto.setAreasForImprovement(annualReview.getAreasForImprovement());
        dto.setGoalsForNextYear(annualReview.getGoalsForNextYear());
        dto.setConsultantComments(annualReview.getConsultantComments());
        dto.setCreatedAt(annualReview.getCreatedAt());
        dto.setUpdatedAt(annualReview.getUpdatedAt());
        return dto;
    }

    private AnnualReview toEntity(AnnualReviewDTO dto) {
        // Utiliser le constructeur sans argument pour éviter les erreurs de compilation
        AnnualReview entity = new AnnualReview();
        entity.setId(dto.getId());
        if (dto.getConsultantId() != null) {
            com.talentwave.domain.User consultant = userRepository.findById(dto.getConsultantId())
                .orElseThrow(() -> new RuntimeException("User (Consultant) not found with id: " + dto.getConsultantId()));
            entity.setConsultant(consultant);
        }
        entity.setReviewYear(dto.getReviewYear());
        entity.setReviewDate(dto.getReviewDate());
        if (dto.getReviewerId() != null) {
            com.talentwave.domain.User reviewer = userRepository.findById(dto.getReviewerId())
                .orElseThrow(() -> new RuntimeException("User (Reviewer) not found with id: " + dto.getReviewerId()));
            entity.setReviewer(reviewer);
        }
        entity.setOverallPerformanceRating(dto.getOverallPerformanceRating());
        entity.setStrengths(dto.getStrengths());
        entity.setAreasForImprovement(dto.getAreasForImprovement());
        entity.setGoalsForNextYear(dto.getGoalsForNextYear());
        entity.setConsultantComments(dto.getConsultantComments());
        // createdAt and updatedAt are handled in save/update logic
        return entity;
    }
}

