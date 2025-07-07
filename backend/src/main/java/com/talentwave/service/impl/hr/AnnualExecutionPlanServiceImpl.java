package com.talentwave.service.impl.hr;

import com.talentwave.domain.hr.AnnualExecutionPlan;
import com.talentwave.repository.hr.AnnualExecutionPlanRepository;
import com.talentwave.service.hr.AnnualExecutionPlanService;
import com.talentwave.service.dto.hr.AnnualExecutionPlanDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
@Transactional
public class AnnualExecutionPlanServiceImpl implements AnnualExecutionPlanService {

    private final Logger log = LoggerFactory.getLogger(AnnualExecutionPlanServiceImpl.class);

    private final AnnualExecutionPlanRepository annualExecutionPlanRepository;

    public AnnualExecutionPlanServiceImpl(AnnualExecutionPlanRepository annualExecutionPlanRepository) {
        this.annualExecutionPlanRepository = annualExecutionPlanRepository;
    }

    @Override
    public AnnualExecutionPlanDTO save(AnnualExecutionPlanDTO annualExecutionPlanDTO) {
        log.debug("Request to save AnnualExecutionPlan : {}", annualExecutionPlanDTO);
        AnnualExecutionPlan annualExecutionPlan = toEntity(annualExecutionPlanDTO);
        if (annualExecutionPlan.getId() == null) { // New entity
            annualExecutionPlan.setCreatedAt(Instant.now());
        } else { // Existing entity
            annualExecutionPlan.setUpdatedAt(Instant.now());
            // Ensure createdAt is preserved if it was already set
            // Créer une variable finale pour utilisation dans la lambda
            final AnnualExecutionPlan finalAnnualExecutionPlan = annualExecutionPlan;
            annualExecutionPlanRepository.findById(annualExecutionPlan.getId()).ifPresent(existing -> finalAnnualExecutionPlan.setCreatedAt(existing.getCreatedAt()));
        }
        annualExecutionPlan = annualExecutionPlanRepository.save(annualExecutionPlan);
        return toDto(annualExecutionPlan);
    }

    @Override
    public Optional<AnnualExecutionPlanDTO> partialUpdate(AnnualExecutionPlanDTO annualExecutionPlanDTO) {
        log.debug("Request to partially update AnnualExecutionPlan : {}", annualExecutionPlanDTO);

        return annualExecutionPlanRepository
            .findById(annualExecutionPlanDTO.getId())
            .map(existingAnnualExecutionPlan -> {
                if (annualExecutionPlanDTO.getYear() != null) {
                    existingAnnualExecutionPlan.setYear(annualExecutionPlanDTO.getYear());
                }
                if (annualExecutionPlanDTO.getRecruitmentTarget() != null) {
                    existingAnnualExecutionPlan.setRecruitmentTarget(annualExecutionPlanDTO.getRecruitmentTarget());
                }
                if (annualExecutionPlanDTO.getObjectives() != null) {
                    existingAnnualExecutionPlan.setObjectives(annualExecutionPlanDTO.getObjectives());
                }
                existingAnnualExecutionPlan.setUpdatedAt(Instant.now());
                return existingAnnualExecutionPlan;
            })
            .map(annualExecutionPlanRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AnnualExecutionPlanDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AnnualExecutionPlans");
        return annualExecutionPlanRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AnnualExecutionPlanDTO> findOne(Long id) {
        log.debug("Request to get AnnualExecutionPlan : {}", id);
        return annualExecutionPlanRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete AnnualExecutionPlan : {}", id);
        annualExecutionPlanRepository.deleteById(id);
    }

    private AnnualExecutionPlanDTO toDto(AnnualExecutionPlan annualExecutionPlan) {
        AnnualExecutionPlanDTO dto = new AnnualExecutionPlanDTO();
        dto.setId(annualExecutionPlan.getId());
        dto.setYear(annualExecutionPlan.getYear());
        dto.setRecruitmentTarget(annualExecutionPlan.getRecruitmentTarget());
        dto.setObjectives(annualExecutionPlan.getObjectives());
        dto.setCreatedAt(annualExecutionPlan.getCreatedAt());
        dto.setUpdatedAt(annualExecutionPlan.getUpdatedAt());
        return dto;
    }

    private AnnualExecutionPlan toEntity(AnnualExecutionPlanDTO dto) {
        AnnualExecutionPlan entity = new AnnualExecutionPlan(
            dto.getYear(),
            dto.getRecruitmentTarget(),
            dto.getObjectives()
        );
        entity.setId(dto.getId());
        entity.setYear(dto.getYear());
        entity.setRecruitmentTarget(dto.getRecruitmentTarget());
        entity.setObjectives(dto.getObjectives());
        // createdAt and updatedAt are handled in save/update logic
        return entity;
    }
}

