package com.talentwave.service.impl.outboarding;

import com.talentwave.domain.User;
import com.talentwave.domain.outboarding.OutboardingStep;
import com.talentwave.repository.UserRepository;
import com.talentwave.repository.outboarding.OutboardingStepRepository;
import com.talentwave.service.dto.outboarding.OutboardingStepDTO;
import com.talentwave.service.outboarding.OutboardingStepService;
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
public class OutboardingStepServiceImpl implements OutboardingStepService {

    private final Logger log = LoggerFactory.getLogger(OutboardingStepServiceImpl.class);

    private final OutboardingStepRepository outboardingStepRepository;
    private final UserRepository userRepository;

    public OutboardingStepServiceImpl(OutboardingStepRepository outboardingStepRepository, UserRepository userRepository) {
        this.outboardingStepRepository = outboardingStepRepository;
        this.userRepository = userRepository;
    }

    @Override
    public OutboardingStepDTO save(OutboardingStepDTO outboardingStepDTO) {
        log.debug("Request to save OutboardingStep : {}", outboardingStepDTO);
        OutboardingStep outboardingStep = toEntity(outboardingStepDTO);
        if (outboardingStep.getId() == null) {
            outboardingStep.setCreatedAt(Instant.now());
        }
        outboardingStep.setUpdatedAt(Instant.now());
        outboardingStep = outboardingStepRepository.save(outboardingStep);
        return toDto(outboardingStep);
    }

    @Override
    public Optional<OutboardingStepDTO> partialUpdate(OutboardingStepDTO outboardingStepDTO) {
        log.debug("Request to partially update OutboardingStep : {}", outboardingStepDTO);

        return outboardingStepRepository
            .findById(outboardingStepDTO.getId())
            .map(existingOutboardingStep -> {
                if (outboardingStepDTO.getConsultantId() != null) {
                    userRepository.findById(outboardingStepDTO.getConsultantId())
                        .ifPresent(existingOutboardingStep::setConsultant);
                }
                if (outboardingStepDTO.getName() != null) {
                    existingOutboardingStep.setName(outboardingStepDTO.getName());
                }
                if (outboardingStepDTO.getDescription() != null) {
                    existingOutboardingStep.setDescription(outboardingStepDTO.getDescription());
                }
                if (outboardingStepDTO.getStepOrder() != null) {
                    existingOutboardingStep.setStepOrder(outboardingStepDTO.getStepOrder());
                }
                if (outboardingStepDTO.getMandatory() != null) {
                    existingOutboardingStep.setMandatory(outboardingStepDTO.getMandatory());
                }
                if (outboardingStepDTO.getCompleted() != null) {
                    existingOutboardingStep.setCompleted(outboardingStepDTO.getCompleted());
                }
                if (outboardingStepDTO.getCompletionDate() != null) {
                    existingOutboardingStep.setCompletionDate(outboardingStepDTO.getCompletionDate());
                }
                if (outboardingStepDTO.getNotes() != null) {
                    existingOutboardingStep.setNotes(outboardingStepDTO.getNotes());
                }
                existingOutboardingStep.setUpdatedAt(Instant.now());
                return existingOutboardingStep;
            })
            .map(outboardingStepRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<OutboardingStepDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OutboardingSteps");
        return outboardingStepRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OutboardingStepDTO> findAllByConsultantId(Long consultantId) {
        log.debug("Request to get all OutboardingSteps for consultant ID: {}", consultantId);
        return outboardingStepRepository.findByConsultantIdOrderByStepOrderAsc(consultantId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<OutboardingStepDTO> findOne(Long id) {
        log.debug("Request to get OutboardingStep : {}", id);
        return outboardingStepRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete OutboardingStep : {}", id);
        outboardingStepRepository.deleteById(id);
    }

    private OutboardingStepDTO toDto(OutboardingStep outboardingStep) {
        OutboardingStepDTO dto = new OutboardingStepDTO();
        dto.setId(outboardingStep.getId());
        if (outboardingStep.getConsultant() != null) {
            dto.setConsultantId(outboardingStep.getConsultant().getId());
            dto.setConsultantName(outboardingStep.getConsultant().getFirstName() + " " + outboardingStep.getConsultant().getLastName());
        }
        dto.setName(outboardingStep.getName());
        dto.setDescription(outboardingStep.getDescription());
        dto.setStepOrder(outboardingStep.getStepOrder());
        dto.setMandatory(outboardingStep.isMandatory());
        dto.setCompleted(outboardingStep.isCompleted());
        dto.setCompletionDate(outboardingStep.getCompletionDate());
        dto.setNotes(outboardingStep.getNotes());
        dto.setCreatedAt(outboardingStep.getCreatedAt());
        dto.setUpdatedAt(outboardingStep.getUpdatedAt());
        return dto;
    }

    private OutboardingStep toEntity(OutboardingStepDTO dto) {
        // Utiliser le constructeur sans argument pour éviter les erreurs de compilation
        OutboardingStep entity = new OutboardingStep();
        entity.setId(dto.getId());
        if (dto.getConsultantId() != null) {
            userRepository.findById(dto.getConsultantId())
                .ifPresent(entity::setConsultant);
        }
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setStepOrder(dto.getStepOrder());
        entity.setMandatory(dto.getMandatory() != null ? dto.getMandatory() : true);
        entity.setCompleted(dto.getCompleted() != null ? dto.getCompleted() : false);
        entity.setCompletionDate(dto.getCompletionDate());
        entity.setNotes(dto.getNotes());
        // createdAt and updatedAt are handled in save method
        return entity;
    }
}
