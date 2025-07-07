package com.talentwave.service.impl.onboarding;

import com.talentwave.domain.User;
import com.talentwave.domain.onboarding.OnboardingStep;
import com.talentwave.repository.UserRepository;
import com.talentwave.repository.onboarding.OnboardingStepRepository;
import com.talentwave.service.dto.onboarding.OnboardingStepDTO;
import com.talentwave.service.onboarding.OnboardingStepService;
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
public class OnboardingStepServiceImpl implements OnboardingStepService {

    private final Logger log = LoggerFactory.getLogger(OnboardingStepServiceImpl.class);

    private final OnboardingStepRepository onboardingStepRepository;
    private final UserRepository userRepository;

    public OnboardingStepServiceImpl(OnboardingStepRepository onboardingStepRepository, UserRepository userRepository) {
        this.onboardingStepRepository = onboardingStepRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OnboardingStepDTO save(OnboardingStepDTO onboardingStepDTO) {
        log.debug("Request to save OnboardingStep : {}", onboardingStepDTO);
        OnboardingStep onboardingStep = toEntity(onboardingStepDTO);
        if (onboardingStep.getId() == null) {
            onboardingStep.setCreatedAt(Instant.now());
        }
        onboardingStep.setUpdatedAt(Instant.now());
        onboardingStep = onboardingStepRepository.save(onboardingStep);
        return toDto(onboardingStep);
    }

    @Override
    public Optional<OnboardingStepDTO> partialUpdate(OnboardingStepDTO onboardingStepDTO) {
        log.debug("Request to partially update OnboardingStep : {}", onboardingStepDTO);

        return onboardingStepRepository
            .findById(onboardingStepDTO.getId())
            .map(existingOnboardingStep -> {
                if (onboardingStepDTO.getConsultantId() != null) {
                    userRepository.findById(onboardingStepDTO.getConsultantId())
                        .ifPresent(existingOnboardingStep::setConsultant);
                }
                if (onboardingStepDTO.getName() != null) {
                    existingOnboardingStep.setName(onboardingStepDTO.getName());
                    existingOnboardingStep.setStepName(onboardingStepDTO.getName());
                }
                if (onboardingStepDTO.getDescription() != null) {
                    existingOnboardingStep.setDescription(onboardingStepDTO.getDescription());
                }
                if (onboardingStepDTO.getDueDate() != null) {
                    existingOnboardingStep.setDueDate(onboardingStepDTO.getDueDate());
                }
                if (onboardingStepDTO.getCompleted() != null) {
                    existingOnboardingStep.setCompleted(onboardingStepDTO.getCompleted());
                }
                if (onboardingStepDTO.getCompletionDate() != null) {
                    existingOnboardingStep.setCompletionDate(onboardingStepDTO.getCompletionDate());
                }
                if (onboardingStepDTO.getNotes() != null) {
                    existingOnboardingStep.setNotes(onboardingStepDTO.getNotes());
                }
                if (onboardingStepDTO.getStepOrder() != null) {
                    existingOnboardingStep.setStepOrder(onboardingStepDTO.getStepOrder());
                    existingOnboardingStep.setOrderIndex(onboardingStepDTO.getStepOrder());
                }
                if (onboardingStepDTO.getMandatory() != null) {
                    existingOnboardingStep.setMandatory(onboardingStepDTO.getMandatory());
                }
                existingOnboardingStep.setUpdatedAt(Instant.now());
                return existingOnboardingStep;
            })
            .map(onboardingStepRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OnboardingStepDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OnboardingSteps");
        return onboardingStepRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OnboardingStepDTO> findAllByConsultantId(Long consultantId) {
        log.debug("Request to get all OnboardingSteps for consultant ID: {}", consultantId);
        return onboardingStepRepository.findByConsultantIdOrderByStepOrderAsc(consultantId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OnboardingStepDTO> findOne(Long id) {
        log.debug("Request to get OnboardingStep : {}", id);
        return onboardingStepRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OnboardingStep : {}", id);
        onboardingStepRepository.deleteById(id);
    }

    private OnboardingStepDTO toDto(OnboardingStep onboardingStep) {
        OnboardingStepDTO dto = new OnboardingStepDTO();
        dto.setId(onboardingStep.getId());
        if (onboardingStep.getConsultant() != null) {
            dto.setConsultantId(onboardingStep.getConsultant().getId());
            dto.setConsultantName(onboardingStep.getConsultant().getFirstName() + " " + onboardingStep.getConsultant().getLastName());
        }
        dto.setName(onboardingStep.getName());
        dto.setDescription(onboardingStep.getDescription());
        dto.setDueDate(onboardingStep.getDueDate());
        dto.setCompleted(onboardingStep.isCompleted());
        dto.setCompletionDate(onboardingStep.getCompletionDate());
        dto.setNotes(onboardingStep.getNotes());
        dto.setStepOrder(onboardingStep.getStepOrder());
        dto.setMandatory(onboardingStep.isMandatory());
        dto.setCreatedAt(onboardingStep.getCreatedAt());
        dto.setUpdatedAt(onboardingStep.getUpdatedAt());
        return dto;
    }

    private OnboardingStep toEntity(OnboardingStepDTO dto) {
        // Utiliser le constructeur sans argument pour éviter les erreurs de compilation
        OnboardingStep entity = new OnboardingStep();
        entity.setId(dto.getId());
        if (dto.getConsultantId() != null) {
            userRepository.findById(dto.getConsultantId())
                .ifPresent(entity::setConsultant);
        }
        entity.setName(dto.getName());
        entity.setStepName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setDueDate(dto.getDueDate());
        entity.setCompleted(dto.getCompleted() != null ? dto.getCompleted() : false);
        entity.setCompletionDate(dto.getCompletionDate());
        entity.setNotes(dto.getNotes());
        entity.setStepOrder(dto.getStepOrder());
        entity.setOrderIndex(dto.getStepOrder());
        entity.setMandatory(dto.getMandatory() != null ? dto.getMandatory() : true);
        // createdAt and updatedAt are handled in save method
        return entity;
    }
}