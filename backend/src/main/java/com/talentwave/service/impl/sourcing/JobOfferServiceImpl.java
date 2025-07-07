package com.talentwave.service.impl.sourcing;

import com.talentwave.domain.hr.JobProfile;
import com.talentwave.domain.sourcing.JobOffer;
import com.talentwave.repository.hr.JobProfileRepository;
import com.talentwave.repository.sourcing.JobOfferRepository;
import com.talentwave.service.sourcing.JobOfferService;
import com.talentwave.service.dto.sourcing.JobOfferDTO;
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
public class JobOfferServiceImpl implements JobOfferService {

    private final Logger log = LoggerFactory.getLogger(JobOfferServiceImpl.class);

    private final JobOfferRepository jobOfferRepository;
    private final JobProfileRepository jobProfileRepository; // To fetch JobProfile for linking

    public JobOfferServiceImpl(JobOfferRepository jobOfferRepository, JobProfileRepository jobProfileRepository) {
        this.jobOfferRepository = jobOfferRepository;
        this.jobProfileRepository = jobProfileRepository;
    }

    @Override
    public JobOfferDTO save(JobOfferDTO jobOfferDTO) {
        log.debug("Request to save JobOffer : {}", jobOfferDTO);
        JobOffer jobOffer = toEntity(jobOfferDTO);
        if (jobOffer.getId() == null) { // New entity
            jobOffer.setCreatedAt(Instant.now());
        } else { // Existing entity
            jobOffer.setUpdatedAt(Instant.now());
            // Créer une variable finale pour utilisation dans la lambda
            final JobOffer finalJobOffer = jobOffer;
            jobOfferRepository.findById(jobOffer.getId()).ifPresent(existing -> finalJobOffer.setCreatedAt(existing.getCreatedAt()));
        }
        jobOffer = jobOfferRepository.save(jobOffer);
        return toDto(jobOffer);
    }

    @Override
    public Optional<JobOfferDTO> partialUpdate(JobOfferDTO jobOfferDTO) {
        log.debug("Request to partially update JobOffer : {}", jobOfferDTO);

        return jobOfferRepository
            .findById(jobOfferDTO.getId())
            .map(existingJobOffer -> {
                if (jobOfferDTO.getTitle() != null) {
                    existingJobOffer.setTitle(jobOfferDTO.getTitle());
                }
                if (jobOfferDTO.getDescription() != null) {
                    existingJobOffer.setDescription(jobOfferDTO.getDescription());
                }
                if (jobOfferDTO.getLocation() != null) {
                    existingJobOffer.setLocation(jobOfferDTO.getLocation());
                }
                if (jobOfferDTO.getStatus() != null) {
                    existingJobOffer.setStatus(jobOfferDTO.getStatus());
                }
                if (jobOfferDTO.getClosingDate() != null) {
                    existingJobOffer.setClosingDate(jobOfferDTO.getClosingDate());
                }
                if (jobOfferDTO.getJobProfileId() != null) {
                    jobProfileRepository.findById(jobOfferDTO.getJobProfileId()).ifPresent(existingJobOffer::setJobProfile);
                }
                existingJobOffer.setUpdatedAt(Instant.now());
                return existingJobOffer;
            })
            .map(jobOfferRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobOfferDTO> findAll(Pageable pageable) {
        log.debug("Request to get all JobOffers");
        return jobOfferRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JobOfferDTO> findOne(Long id) {
        log.debug("Request to get JobOffer : {}", id);
        return jobOfferRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete JobOffer : {}", id);
        jobOfferRepository.deleteById(id);
    }

    private JobOfferDTO toDto(JobOffer jobOffer) {
        JobOfferDTO dto = new JobOfferDTO();
        dto.setId(jobOffer.getId());
        dto.setTitle(jobOffer.getTitle());
        dto.setDescription(jobOffer.getDescription());
        dto.setLocation(jobOffer.getLocation());
        dto.setStatus(jobOffer.getStatus());
        dto.setClosingDate(jobOffer.getClosingDate());
        if (jobOffer.getJobProfile() != null) {
            dto.setJobProfileId(jobOffer.getJobProfile().getId());
            dto.setJobProfileTitle(jobOffer.getJobProfile().getTitle());
        }
        dto.setCreatedAt(jobOffer.getCreatedAt());
        dto.setUpdatedAt(jobOffer.getUpdatedAt());
        return dto;
    }

    private JobOffer toEntity(JobOfferDTO dto) {
        JobOffer entity = new JobOffer();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setLocation(dto.getLocation());
        entity.setStatus(dto.getStatus());
        entity.setClosingDate(dto.getClosingDate());
        if (dto.getJobProfileId() != null) {
            JobProfile jobProfile = jobProfileRepository.findById(dto.getJobProfileId())
                .orElseThrow(() -> new RuntimeException("JobProfile not found with id: " + dto.getJobProfileId())); // Or handle more gracefully
            entity.setJobProfile(jobProfile);
        }
        // createdAt and updatedAt are handled in save/update logic
        return entity;
    }
}

