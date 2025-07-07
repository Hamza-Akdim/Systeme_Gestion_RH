package com.talentwave.service.impl.business;

import com.talentwave.domain.User;
import com.talentwave.domain.business.BusinessSurvey;
import com.talentwave.domain.business.Prospect;
import com.talentwave.repository.UserRepository;
import com.talentwave.repository.business.BusinessSurveyRepository;
import com.talentwave.repository.business.ProspectRepository;
import com.talentwave.service.business.BusinessSurveyService;
import com.talentwave.service.dto.business.BusinessSurveyDTO;
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
public class BusinessSurveyServiceImpl implements BusinessSurveyService {

    private final Logger log = LoggerFactory.getLogger(BusinessSurveyServiceImpl.class);

    private final BusinessSurveyRepository businessSurveyRepository;
    private final ProspectRepository prospectRepository;
    private final UserRepository userRepository;

    public BusinessSurveyServiceImpl(BusinessSurveyRepository businessSurveyRepository, 
                                   ProspectRepository prospectRepository, 
                                   UserRepository userRepository) {
        this.businessSurveyRepository = businessSurveyRepository;
        this.prospectRepository = prospectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public BusinessSurveyDTO save(BusinessSurveyDTO businessSurveyDTO) {
        log.debug("Request to save BusinessSurvey : {}", businessSurveyDTO);
        BusinessSurvey businessSurvey = toEntity(businessSurveyDTO);
        if (businessSurvey.getId() == null) { // New entity
            businessSurvey.setCreatedAt(Instant.now());
        } else { // Existing entity
            businessSurvey.setUpdatedAt(Instant.now());
            // Créer une variable finale pour utilisation dans la lambda
            final BusinessSurvey finalBusinessSurvey = businessSurvey;
            businessSurveyRepository.findById(businessSurvey.getId()).ifPresent(existing -> finalBusinessSurvey.setCreatedAt(existing.getCreatedAt()));
        }
        businessSurvey = businessSurveyRepository.save(businessSurvey);
        return toDto(businessSurvey);
    }

    @Override
    public Optional<BusinessSurveyDTO> partialUpdate(BusinessSurveyDTO businessSurveyDTO) {
        log.debug("Request to partially update BusinessSurvey : {}", businessSurveyDTO);

        return businessSurveyRepository
            .findById(businessSurveyDTO.getId())
            .map(existingBusinessSurvey -> {
                if (businessSurveyDTO.getProspectId() != null) {
                    prospectRepository.findById(businessSurveyDTO.getProspectId()).ifPresent(existingBusinessSurvey::setProspect);
                }
                if (businessSurveyDTO.getSurveyDate() != null) {
                    existingBusinessSurvey.setSurveyDate(businessSurveyDTO.getSurveyDate());
                }
                if (businessSurveyDTO.getSubject() != null) {
                    existingBusinessSurvey.setSubject(businessSurveyDTO.getSubject());
                }
                if (businessSurveyDTO.getSummary() != null) {
                    existingBusinessSurvey.setSummary(businessSurveyDTO.getSummary());
                }
                if (businessSurveyDTO.getPotentialNeeds() != null) {
                    existingBusinessSurvey.setPotentialNeeds(businessSurveyDTO.getPotentialNeeds());
                }
                if (businessSurveyDTO.getNextSteps() != null) {
                    existingBusinessSurvey.setNextSteps(businessSurveyDTO.getNextSteps());
                }
                if (businessSurveyDTO.getConductedById() != null) {
                    userRepository.findById(businessSurveyDTO.getConductedById()).ifPresent(existingBusinessSurvey::setConductedBy);
                }
                existingBusinessSurvey.setUpdatedAt(Instant.now());
                return existingBusinessSurvey;
            })
            .map(businessSurveyRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<BusinessSurveyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all BusinessSurveys");
        return businessSurveyRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BusinessSurveyDTO> findAllByProspectId(Long prospectId) {
        log.debug("Request to get all BusinessSurveys for Prospect ID: {}", prospectId);
        return businessSurveyRepository.findByProspectId(prospectId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BusinessSurveyDTO> findOne(Long id) {
        log.debug("Request to get BusinessSurvey : {}", id);
        return businessSurveyRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete BusinessSurvey : {}", id);
        businessSurveyRepository.deleteById(id);
    }

    private BusinessSurveyDTO toDto(BusinessSurvey businessSurvey) {
        BusinessSurveyDTO dto = new BusinessSurveyDTO();
        dto.setId(businessSurvey.getId());
        if (businessSurvey.getProspect() != null) {
            dto.setProspectId(businessSurvey.getProspect().getId());
            dto.setProspectCompanyName(businessSurvey.getProspect().getCompanyName());
        }
        dto.setSurveyDate(businessSurvey.getSurveyDate());
        dto.setSubject(businessSurvey.getSubject());
        dto.setSummary(businessSurvey.getSummary());
        dto.setPotentialNeeds(businessSurvey.getPotentialNeeds());
        dto.setNextSteps(businessSurvey.getNextSteps());
        if (businessSurvey.getConductedBy() != null) {
            dto.setConductedById(businessSurvey.getConductedBy().getId());
            dto.setConductedByName(businessSurvey.getConductedBy().getUsername()); // Or full name
        }
        dto.setCreatedAt(businessSurvey.getCreatedAt());
        dto.setUpdatedAt(businessSurvey.getUpdatedAt());
        return dto;
    }

    private BusinessSurvey toEntity(BusinessSurveyDTO dto) {
        // Utiliser le constructeur sans argument pour éviter les erreurs de compilation
        BusinessSurvey entity = new BusinessSurvey();
        entity.setId(dto.getId());
        if (dto.getProspectId() != null) {
            Prospect prospect = prospectRepository.findById(dto.getProspectId())
                .orElseThrow(() -> new RuntimeException("Prospect not found with id: " + dto.getProspectId()));
            entity.setProspect(prospect);
        }
        entity.setSurveyDate(dto.getSurveyDate());
        entity.setSubject(dto.getSubject());
        entity.setSummary(dto.getSummary());
        entity.setPotentialNeeds(dto.getPotentialNeeds());
        entity.setNextSteps(dto.getNextSteps());
        if (dto.getConductedById() != null) {
            com.talentwave.domain.User conductedBy = userRepository.findById(dto.getConductedById())
                .orElseThrow(() -> new RuntimeException("User (ConductedBy) not found with id: " + dto.getConductedById()));
            entity.setConductedBy(conductedBy);
        }
        // createdAt and updatedAt are handled in save/update logic
        return entity;
    }
}

