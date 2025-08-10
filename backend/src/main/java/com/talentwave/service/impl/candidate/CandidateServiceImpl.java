package com.talentwave.service.impl.candidate;

import com.talentwave.domain.candidate.Candidate;
import com.talentwave.repository.candidate.CandidateRepository;
import com.talentwave.service.candidate.CandidateService;
import com.talentwave.service.dto.profilCV.CandidateDTO;
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
public class CandidateServiceImpl implements CandidateService {

    private final Logger log = LoggerFactory.getLogger(CandidateServiceImpl.class);

    private final CandidateRepository candidateRepository;

    public CandidateServiceImpl(CandidateRepository candidateRepository) {
        this.candidateRepository = candidateRepository;
    }

    @Override
    public CandidateDTO save(CandidateDTO candidateDTO) {
        log.debug("Request to save Candidate : {}", candidateDTO);
        Candidate candidate = toEntity(candidateDTO);
        if (candidate.getId() == null) { // New entity
            if (candidateRepository.existsByEmail(candidate.getEmail())) {
                throw new RuntimeException("Email already exists for another candidate."); // Replace with custom exception
            }
            candidate.setCreatedAt(Instant.now());
        } else { // Existing entity
            candidate.setUpdatedAt(Instant.now());
            // Créer une variable finale pour utilisation dans la lambda
            final Candidate finalCandidate = candidate;
            candidateRepository.findById(candidate.getId()).ifPresent(existing -> {
                finalCandidate.setCreatedAt(existing.getCreatedAt());
                if (!existing.getEmail().equals(finalCandidate.getEmail()) && candidateRepository.existsByEmail(finalCandidate.getEmail())) {
                     throw new RuntimeException("Email already exists for another candidate."); // Replace with custom exception
                }
            });
        }
        candidate = candidateRepository.save(candidate);
        return toDto(candidate);
    }

    @Override
    public Optional<CandidateDTO> partialUpdate(CandidateDTO candidateDTO) {
        log.debug("Request to partially update Candidate : {}", candidateDTO);

        return candidateRepository
            .findById(candidateDTO.getId())
            .map(existingCandidate -> {
                if (candidateDTO.getFirstName() != null) {
                    existingCandidate.setFirstName(candidateDTO.getFirstName());
                }
                if (candidateDTO.getLastName() != null) {
                    existingCandidate.setLastName(candidateDTO.getLastName());
                }
                if (candidateDTO.getEmail() != null) {
                    if (!existingCandidate.getEmail().equals(candidateDTO.getEmail()) && candidateRepository.existsByEmail(candidateDTO.getEmail())) {
                        throw new RuntimeException("Email already exists for another candidate.");
                    }
                    existingCandidate.setEmail(candidateDTO.getEmail());
                }
                if (candidateDTO.getPhoneNumber() != null) {
                    existingCandidate.setPhoneNumber(candidateDTO.getPhoneNumber());
                }
                if (candidateDTO.getResumeUrl() != null) {
                    existingCandidate.setResumeUrl(candidateDTO.getResumeUrl());
                }
                if (candidateDTO.getCoverLetterText() != null) {
                    existingCandidate.setCoverLetterText(candidateDTO.getCoverLetterText());
                }
                if (candidateDTO.getLinkedInProfileUrl() != null) {
                    existingCandidate.setLinkedInProfileUrl(candidateDTO.getLinkedInProfileUrl());
                }
                existingCandidate.setUpdatedAt(Instant.now());
                return existingCandidate;
            })
            .map(candidateRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CandidateDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Candidates");
        return candidateRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CandidateDTO> findOne(Long id) {
        log.debug("Request to get Candidate : {}", id);
        return candidateRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Candidate : {}", id);
        candidateRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CandidateDTO> findByEmail(String email) {
        log.debug("Request to get Candidate by email : {}", email);
        return candidateRepository.findByEmail(email).map(this::toDto);
    }

    private CandidateDTO toDto(Candidate candidate) {
        CandidateDTO dto = new CandidateDTO();
        dto.setId(candidate.getId());
        dto.setFirstName(candidate.getFirstName());
        dto.setLastName(candidate.getLastName());
        dto.setEmail(candidate.getEmail());
        dto.setPhoneNumber(candidate.getPhoneNumber());
        dto.setResumeUrl(candidate.getResumeUrl());
        dto.setCoverLetterText(candidate.getCoverLetterText());
        dto.setLinkedInProfileUrl(candidate.getLinkedInProfileUrl());
        dto.setCreatedAt(candidate.getCreatedAt());
        dto.setUpdatedAt(candidate.getUpdatedAt());
        return dto;
    }

    private Candidate toEntity(CandidateDTO dto) {
        // Utiliser le constructeur sans argument pour éviter les erreurs de compilation
        Candidate entity = new Candidate();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setResumeUrl(dto.getResumeUrl());
        entity.setCoverLetterText(dto.getCoverLetterText());
        entity.setLinkedInProfileUrl(dto.getLinkedInProfileUrl());
        // createdAt and updatedAt are handled in save/update logic
        return entity;
    }
}

