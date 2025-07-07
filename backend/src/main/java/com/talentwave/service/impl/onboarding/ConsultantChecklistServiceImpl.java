package com.talentwave.service.impl.onboarding;

import com.talentwave.domain.User;
import com.talentwave.domain.onboarding.ConsultantChecklist;
import com.talentwave.domain.onboarding.OnboardingStep;
import com.talentwave.repository.UserRepository;
import com.talentwave.repository.onboarding.ConsultantChecklistRepository;
import com.talentwave.service.onboarding.ConsultantChecklistService;
import com.talentwave.service.dto.onboarding.ConsultantChecklistDTO;
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
public class ConsultantChecklistServiceImpl implements ConsultantChecklistService {

    private final Logger log = LoggerFactory.getLogger(ConsultantChecklistServiceImpl.class);

    private final ConsultantChecklistRepository consultantChecklistRepository;
    private final UserRepository userRepository;

    public ConsultantChecklistServiceImpl(ConsultantChecklistRepository consultantChecklistRepository, UserRepository userRepository) {
        this.consultantChecklistRepository = consultantChecklistRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ConsultantChecklistDTO save(ConsultantChecklistDTO consultantChecklistDTO) {
        log.debug("Request to save ConsultantChecklist : {}", consultantChecklistDTO);
        ConsultantChecklist consultantChecklist = toEntity(consultantChecklistDTO);
        if (consultantChecklist.getId() == null) { // New entity
            consultantChecklist.setCreatedAt(Instant.now());
        } else { // Existing entity
            consultantChecklist.setUpdatedAt(Instant.now());
            // Créer une variable finale pour utilisation dans la lambda
            final ConsultantChecklist finalConsultantChecklist = consultantChecklist;
            consultantChecklistRepository.findById(consultantChecklist.getId()).ifPresent(existing -> finalConsultantChecklist.setCreatedAt(existing.getCreatedAt()));
        }
        consultantChecklist = consultantChecklistRepository.save(consultantChecklist);
        return toDto(consultantChecklist);
    }

    @Override
    public Optional<ConsultantChecklistDTO> partialUpdate(ConsultantChecklistDTO consultantChecklistDTO) {
        log.debug("Request to partially update ConsultantChecklist : {}", consultantChecklistDTO);

        return consultantChecklistRepository
            .findById(consultantChecklistDTO.getId())
            .map(existingConsultantChecklist -> {
                if (consultantChecklistDTO.getConsultantId() != null) {
                    userRepository.findById(consultantChecklistDTO.getConsultantId()).ifPresent(existingConsultantChecklist::setConsultant);
                }
                if (consultantChecklistDTO.getTaskName() != null) {
                    existingConsultantChecklist.setTaskName(consultantChecklistDTO.getTaskName());
                }
                if (consultantChecklistDTO.getDescription() != null) {
                    existingConsultantChecklist.setDescription(consultantChecklistDTO.getDescription());
                }
                // Handle boolean completed field
                existingConsultantChecklist.setCompleted(consultantChecklistDTO.isCompleted());

                if (consultantChecklistDTO.getCompletionDate() != null) {
                    existingConsultantChecklist.setCompletionDate(consultantChecklistDTO.getCompletionDate());
                }
                if (consultantChecklistDTO.getVerifiedById() != null) {
                    userRepository.findById(consultantChecklistDTO.getVerifiedById()).ifPresent(existingConsultantChecklist::setVerifiedBy);
                }
                if (consultantChecklistDTO.getNotes() != null) {
                    existingConsultantChecklist.setNotes(consultantChecklistDTO.getNotes());
                }
                existingConsultantChecklist.setUpdatedAt(Instant.now());
                return existingConsultantChecklist;
            })
            .map(consultantChecklistRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConsultantChecklistDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ConsultantChecklists");
        return consultantChecklistRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultantChecklistDTO> findAllByConsultantId(Long consultantId) {
        log.debug("Request to get all ConsultantChecklists for Consultant ID: {}", consultantId);
        return consultantChecklistRepository.findByConsultantId(consultantId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ConsultantChecklistDTO> findOne(Long id) {
        log.debug("Request to get ConsultantChecklist : {}", id);
        return consultantChecklistRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ConsultantChecklist : {}", id);
        consultantChecklistRepository.deleteById(id);
    }

    private ConsultantChecklistDTO toDto(ConsultantChecklist consultantChecklist) {
        ConsultantChecklistDTO dto = new ConsultantChecklistDTO();
        dto.setId(consultantChecklist.getId());
        if (consultantChecklist.getConsultant() != null) {
            dto.setConsultantId(consultantChecklist.getConsultant().getId());
            dto.setConsultantName(consultantChecklist.getConsultant().getUsername()); // Or full name
        }
        dto.setTaskName(consultantChecklist.getTaskName());
        dto.setDescription(consultantChecklist.getDescription());
        dto.setCompleted(consultantChecklist.isCompleted());
        dto.setCompletionDate(consultantChecklist.getCompletionDate());
        if (consultantChecklist.getVerifiedBy() != null) {
            dto.setVerifiedById(consultantChecklist.getVerifiedBy().getId());
            dto.setVerifiedByName(consultantChecklist.getVerifiedBy().getUsername()); // Or full name
        }
        dto.setNotes(consultantChecklist.getNotes());
        dto.setCreatedAt(consultantChecklist.getCreatedAt());
        dto.setUpdatedAt(consultantChecklist.getUpdatedAt());
        return dto;
    }

    private ConsultantChecklist toEntity(ConsultantChecklistDTO dto) {
        // Utiliser le constructeur sans argument pour éviter les erreurs de compilation
        ConsultantChecklist entity = new ConsultantChecklist();
        entity.setId(dto.getId());
        if (dto.getConsultantId() != null) {
            com.talentwave.domain.User consultant = userRepository.findById(dto.getConsultantId())
                .orElseThrow(() -> new RuntimeException("User (Consultant) not found with id: " + dto.getConsultantId()));
            entity.setConsultant(consultant);
        }
        entity.setTaskName(dto.getTaskName());
        entity.setDescription(dto.getDescription());
        entity.setCompleted(dto.isCompleted());
        entity.setCompletionDate(dto.getCompletionDate());
        if (dto.getVerifiedById() != null) {
            com.talentwave.domain.User verifiedBy = userRepository.findById(dto.getVerifiedById())
                .orElseThrow(() -> new RuntimeException("User (VerifiedBy) not found with id: " + dto.getVerifiedById()));
            entity.setVerifiedBy(verifiedBy);
        }
        entity.setNotes(dto.getNotes());
        // createdAt and updatedAt are handled in save/update logic
        return entity;
    }
}

