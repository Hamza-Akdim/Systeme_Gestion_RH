package com.talentwave.service.impl.evaluation;

import com.talentwave.domain.User;
import com.talentwave.domain.evaluation.SkillAssessment;
import com.talentwave.repository.UserRepository;
import com.talentwave.repository.evaluation.SkillAssessmentRepository;
import com.talentwave.service.evaluation.SkillAssessmentService;
import com.talentwave.service.dto.evaluation.SkillAssessmentDTO;
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
public class SkillAssessmentServiceImpl implements SkillAssessmentService {

    private final Logger log = LoggerFactory.getLogger(SkillAssessmentServiceImpl.class);

    private final SkillAssessmentRepository skillAssessmentRepository;
    private final UserRepository userRepository;

    public SkillAssessmentServiceImpl(SkillAssessmentRepository skillAssessmentRepository, UserRepository userRepository) {
        this.skillAssessmentRepository = skillAssessmentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SkillAssessmentDTO save(SkillAssessmentDTO skillAssessmentDTO) {
        log.debug("Request to save SkillAssessment : {}", skillAssessmentDTO);
        SkillAssessment skillAssessment = toEntity(skillAssessmentDTO);
        if (skillAssessment.getId() == null) { // New entity
            skillAssessment.setCreatedAt(Instant.now());
        } else { // Existing entity
            skillAssessment.setUpdatedAt(Instant.now());
            // Créer une variable finale pour utilisation dans la lambda
            final SkillAssessment finalSkillAssessment = skillAssessment;
            skillAssessmentRepository.findById(skillAssessment.getId()).ifPresent(existing -> finalSkillAssessment.setCreatedAt(existing.getCreatedAt()));
        }
        skillAssessment = skillAssessmentRepository.save(skillAssessment);
        return toDto(skillAssessment);
    }

    @Override
    public Optional<SkillAssessmentDTO> partialUpdate(SkillAssessmentDTO skillAssessmentDTO) {
        log.debug("Request to partially update SkillAssessment : {}", skillAssessmentDTO);

        return skillAssessmentRepository
            .findById(skillAssessmentDTO.getId())
            .map(existingSkillAssessment -> {
                if (skillAssessmentDTO.getConsultantId() != null) {
                    userRepository.findById(skillAssessmentDTO.getConsultantId()).ifPresent(existingSkillAssessment::setConsultant);
                }
                if (skillAssessmentDTO.getSkillName() != null) {
                    existingSkillAssessment.setSkillName(skillAssessmentDTO.getSkillName());
                }
                if (skillAssessmentDTO.getProficiencyLevel() != null) {
                    existingSkillAssessment.setProficiencyLevel(skillAssessmentDTO.getProficiencyLevel());
                }
                if (skillAssessmentDTO.getAssessmentDate() != null) {
                    existingSkillAssessment.setAssessmentDate(skillAssessmentDTO.getAssessmentDate());
                }
                if (skillAssessmentDTO.getAssessorId() != null) {
                    userRepository.findById(skillAssessmentDTO.getAssessorId()).ifPresent(existingSkillAssessment::setAssessor);
                }
                if (skillAssessmentDTO.getComments() != null) {
                    existingSkillAssessment.setComments(skillAssessmentDTO.getComments());
                }
                existingSkillAssessment.setUpdatedAt(Instant.now());
                return existingSkillAssessment;
            })
            .map(skillAssessmentRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SkillAssessmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SkillAssessments");
        return skillAssessmentRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SkillAssessmentDTO> findAllByConsultantId(Long consultantId) {
        log.debug("Request to get all SkillAssessments for Consultant ID: {}", consultantId);
        return skillAssessmentRepository.findByConsultantId(consultantId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SkillAssessmentDTO> findOne(Long id) {
        log.debug("Request to get SkillAssessment : {}", id);
        return skillAssessmentRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SkillAssessment : {}", id);
        skillAssessmentRepository.deleteById(id);
    }

    private SkillAssessmentDTO toDto(SkillAssessment skillAssessment) {
        SkillAssessmentDTO dto = new SkillAssessmentDTO();
        dto.setId(skillAssessment.getId());
        if (skillAssessment.getConsultant() != null) {
            dto.setConsultantId(skillAssessment.getConsultant().getId());
            dto.setConsultantName(skillAssessment.getConsultant().getUsername()); // Or full name
        }
        dto.setSkillName(skillAssessment.getSkillName());
        dto.setProficiencyLevel(skillAssessment.getProficiencyLevel());
        dto.setAssessmentDate(skillAssessment.getAssessmentDate());
        if (skillAssessment.getAssessor() != null) {
            dto.setAssessorId(skillAssessment.getAssessor().getId());
            dto.setAssessorName(skillAssessment.getAssessor().getUsername()); // Or full name
        }
        dto.setComments(skillAssessment.getComments());
        dto.setCreatedAt(skillAssessment.getCreatedAt());
        dto.setUpdatedAt(skillAssessment.getUpdatedAt());
        return dto;
    }

    private SkillAssessment toEntity(SkillAssessmentDTO dto) {
        // Utiliser le constructeur sans argument pour éviter les erreurs de compilation
        SkillAssessment entity = new SkillAssessment();
        
        // Puis configuration des propriétés
        entity.setId(dto.getId());
        if (dto.getConsultantId() != null) {
            com.talentwave.domain.User consultant = userRepository.findById(dto.getConsultantId())
                .orElseThrow(() -> new RuntimeException("User (Consultant) not found with id: " + dto.getConsultantId()));
            entity.setConsultant(consultant);
        }
        entity.setSkillName(dto.getSkillName());
        entity.setProficiencyLevel(dto.getProficiencyLevel());
        entity.setAssessmentDate(dto.getAssessmentDate());
        if (dto.getAssessorId() != null) {
            com.talentwave.domain.User assessor = userRepository.findById(dto.getAssessorId())
                .orElseThrow(() -> new RuntimeException("User (Assessor) not found with id: " + dto.getAssessorId()));
            entity.setAssessor(assessor);
        }
        entity.setComments(dto.getComments());
        // createdAt and updatedAt are handled in save/update logic
        return entity;
    }
}

