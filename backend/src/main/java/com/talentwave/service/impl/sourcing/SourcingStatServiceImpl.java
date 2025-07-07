package com.talentwave.service.impl.sourcing;

import com.talentwave.domain.User;
import com.talentwave.domain.sourcing.JobOffer;
import com.talentwave.domain.sourcing.SourcingStat;
import com.talentwave.repository.UserRepository;
import com.talentwave.repository.sourcing.JobOfferRepository;
import com.talentwave.repository.sourcing.SourcingStatRepository;
import com.talentwave.service.sourcing.SourcingStatService;
import com.talentwave.service.dto.sourcing.SourcingStatDTO;
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
public class SourcingStatServiceImpl implements SourcingStatService {

    private final Logger log = LoggerFactory.getLogger(SourcingStatServiceImpl.class);

    private final SourcingStatRepository sourcingStatRepository;
    private final JobOfferRepository jobOfferRepository;
    private final UserRepository userRepository;

    public SourcingStatServiceImpl(SourcingStatRepository sourcingStatRepository, 
                                   JobOfferRepository jobOfferRepository, 
                                   UserRepository userRepository) {
        this.sourcingStatRepository = sourcingStatRepository;
        this.jobOfferRepository = jobOfferRepository;
        this.userRepository = userRepository;
    }

    @Override
    public SourcingStatDTO save(SourcingStatDTO sourcingStatDTO) {
        log.debug("Request to save SourcingStat : {}", sourcingStatDTO);
        SourcingStat sourcingStat = toEntity(sourcingStatDTO);
        if (sourcingStat.getId() == null) { // New entity
            sourcingStat.setCreatedAt(Instant.now());
        }
        // For updates, createdAt is preserved, updatedAt could be set if the entity had it.
        // SourcingStat entity does not have an updatedAt field in the provided definition.
        sourcingStat = sourcingStatRepository.save(sourcingStat);
        return toDto(sourcingStat);
    }

    @Override
    public Optional<SourcingStatDTO> partialUpdate(SourcingStatDTO sourcingStatDTO) {
        log.debug("Request to partially update SourcingStat : {}", sourcingStatDTO);

        return sourcingStatRepository
            .findById(sourcingStatDTO.getId())
            .map(existingSourcingStat -> {
                if (sourcingStatDTO.getJobOfferId() != null) {
                    jobOfferRepository.findById(sourcingStatDTO.getJobOfferId()).ifPresent(existingSourcingStat::setJobOffer);
                }
                if (sourcingStatDTO.getChannel() != null) {
                    existingSourcingStat.setChannel(sourcingStatDTO.getChannel());
                }
                if (sourcingStatDTO.getCandidatesSourced() != null) {
                    existingSourcingStat.setCandidatesSourced(sourcingStatDTO.getCandidatesSourced());
                }
                if (sourcingStatDTO.getApplicationsReceived() != null) {
                    existingSourcingStat.setApplicationsReceived(sourcingStatDTO.getApplicationsReceived());
                }
                if (sourcingStatDTO.getStatDate() != null) {
                    existingSourcingStat.setStatDate(sourcingStatDTO.getStatDate());
                }
                if (sourcingStatDTO.getHrUserId() != null) {
                    userRepository.findById(sourcingStatDTO.getHrUserId()).ifPresent(existingSourcingStat::setHrUser);
                }
                // No updatedAt field in SourcingStat entity
                return existingSourcingStat;
            })
            .map(sourcingStatRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SourcingStatDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SourcingStats");
        return sourcingStatRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SourcingStatDTO> findAllByJobOfferId(Long jobOfferId) {
        log.debug("Request to get all SourcingStats for JobOffer ID: {}", jobOfferId);
        return sourcingStatRepository.findByJobOfferId(jobOfferId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SourcingStatDTO> findOne(Long id) {
        log.debug("Request to get SourcingStat : {}", id);
        return sourcingStatRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete SourcingStat : {}", id);
        sourcingStatRepository.deleteById(id);
    }

    private SourcingStatDTO toDto(SourcingStat sourcingStat) {
        SourcingStatDTO dto = new SourcingStatDTO();
        dto.setId(sourcingStat.getId());
        if (sourcingStat.getJobOffer() != null) {
            dto.setJobOfferId(sourcingStat.getJobOffer().getId());
            dto.setJobOfferTitle(sourcingStat.getJobOffer().getTitle());
        }
        dto.setChannel(sourcingStat.getChannel());
        dto.setCandidatesSourced(sourcingStat.getCandidatesSourced());
        dto.setApplicationsReceived(sourcingStat.getApplicationsReceived());
        dto.setStatDate(sourcingStat.getStatDate());
        if (sourcingStat.getHrUser() != null) {
            dto.setHrUserId(sourcingStat.getHrUser().getId());
            dto.setHrUserName(sourcingStat.getHrUser().getUsername());
        }
        dto.setCreatedAt(sourcingStat.getCreatedAt());
        return dto;
    }

    private SourcingStat toEntity(SourcingStatDTO dto) {
        SourcingStat entity = new SourcingStat();
        entity.setId(dto.getId());
        if (dto.getJobOfferId() != null) {
            JobOffer jobOffer = jobOfferRepository.findById(dto.getJobOfferId())
                .orElseThrow(() -> new RuntimeException("JobOffer not found with id: " + dto.getJobOfferId()));
            entity.setJobOffer(jobOffer);
        }
        entity.setChannel(dto.getChannel());
        entity.setCandidatesSourced(dto.getCandidatesSourced());
        entity.setApplicationsReceived(dto.getApplicationsReceived());
        entity.setStatDate(dto.getStatDate());
        if (dto.getHrUserId() != null) {
            User hrUser = userRepository.findById(dto.getHrUserId())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + dto.getHrUserId()));
            entity.setHrUser(hrUser);
        }
        // createdAt is handled in save logic
        return entity;
    }
}

