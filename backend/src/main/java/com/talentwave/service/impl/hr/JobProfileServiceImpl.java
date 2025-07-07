package com.talentwave.service.impl.hr;

import com.talentwave.domain.hr.JobProfile;
import com.talentwave.repository.hr.JobProfileRepository;
import com.talentwave.service.hr.JobProfileService;
import com.talentwave.service.dto.hr.JobProfileDTO;
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
public class JobProfileServiceImpl implements JobProfileService {

    private final Logger log = LoggerFactory.getLogger(JobProfileServiceImpl.class);

    private final JobProfileRepository jobProfileRepository;

    public JobProfileServiceImpl(JobProfileRepository jobProfileRepository) {
        this.jobProfileRepository = jobProfileRepository;
    }

    @Override
    public JobProfileDTO save(JobProfileDTO jobProfileDTO) {
        log.debug("Request to save JobProfile : {}", jobProfileDTO);
        JobProfile jobProfile = toEntity(jobProfileDTO);
        if (jobProfile.getId() == null) { // New entity
            jobProfile.setCreatedAt(Instant.now());
        } else { // Existing entity
            jobProfile.setUpdatedAt(Instant.now());
            // Ensure createdAt is preserved if it was already set
            // Créer une variable finale pour utilisation dans la lambda
            final JobProfile finalJobProfile = jobProfile;
            jobProfileRepository.findById(jobProfile.getId()).ifPresent(existing -> finalJobProfile.setCreatedAt(existing.getCreatedAt()));
        }
        jobProfile = jobProfileRepository.save(jobProfile);
        return toDto(jobProfile);
    }

    @Override
    public Optional<JobProfileDTO> partialUpdate(JobProfileDTO jobProfileDTO) {
        log.debug("Request to partially update JobProfile : {}", jobProfileDTO);

        return jobProfileRepository
            .findById(jobProfileDTO.getId())
            .map(existingJobProfile -> {
                if (jobProfileDTO.getTitle() != null) {
                    existingJobProfile.setTitle(jobProfileDTO.getTitle());
                }
                if (jobProfileDTO.getDescription() != null) {
                    existingJobProfile.setDescription(jobProfileDTO.getDescription());
                }
                if (jobProfileDTO.getRequirements() != null) {
                    existingJobProfile.setRequirements(jobProfileDTO.getRequirements());
                }
                existingJobProfile.setUpdatedAt(Instant.now());
                return existingJobProfile;
            })
            .map(jobProfileRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobProfileDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobProfiles");
        return jobProfileRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JobProfileDTO> findOne(Long id) {
        log.debug("Request to get JobProfile : {}", id);
        return jobProfileRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobProfile : {}", id);
        jobProfileRepository.deleteById(id);
    }

    private JobProfileDTO toDto(JobProfile jobProfile) {
        JobProfileDTO dto = new JobProfileDTO();
        dto.setId(jobProfile.getId());
        dto.setTitle(jobProfile.getTitle());
        dto.setDescription(jobProfile.getDescription());
        dto.setRequirements(jobProfile.getRequirements());
        dto.setCreatedAt(jobProfile.getCreatedAt());
        dto.setUpdatedAt(jobProfile.getUpdatedAt());
        return dto;
    }

    private JobProfile toEntity(JobProfileDTO dto) {
        JobProfile entity = new JobProfile(
            dto.getTitle(),
            dto.getDescription(),
            dto.getRequirements()
        );
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setRequirements(dto.getRequirements());
        // createdAt and updatedAt are handled in save/update logic
        return entity;
    }
}

