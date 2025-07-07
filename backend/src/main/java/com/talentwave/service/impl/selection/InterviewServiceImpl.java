package com.talentwave.service.impl.selection;

import com.talentwave.domain.User;
import com.talentwave.domain.candidate.Application;
import com.talentwave.domain.selection.Interview;
import com.talentwave.repository.UserRepository;
import com.talentwave.repository.candidate.ApplicationRepository;
import com.talentwave.repository.selection.InterviewRepository;
import com.talentwave.service.selection.InterviewService;
import com.talentwave.service.dto.selection.InterviewDTO;
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
public class InterviewServiceImpl implements InterviewService {

    private final Logger log = LoggerFactory.getLogger(InterviewServiceImpl.class);

    private final InterviewRepository interviewRepository;
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;

    public InterviewServiceImpl(InterviewRepository interviewRepository, 
                              ApplicationRepository applicationRepository, 
                              UserRepository userRepository) {
        this.interviewRepository = interviewRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public InterviewDTO save(InterviewDTO interviewDTO) {
        log.debug("Request to save Interview : {}", interviewDTO);
        Interview interview = toEntity(interviewDTO);
        if (interview.getId() == null) { // New entity
            interview.setCreatedAt(Instant.now());
        } else { // Existing entity
            interview.setUpdatedAt(Instant.now());
            // Créer une variable finale pour utilisation dans la lambda
            final Interview finalInterview = interview;
            interviewRepository.findById(interview.getId()).ifPresent(existing -> finalInterview.setCreatedAt(existing.getCreatedAt()));
        }
        interview = interviewRepository.save(interview);
        return toDto(interview);
    }

    @Override
    public Optional<InterviewDTO> partialUpdate(InterviewDTO interviewDTO) {
        log.debug("Request to partially update Interview : {}", interviewDTO);

        return interviewRepository
            .findById(interviewDTO.getId())
            .map(existingInterview -> {
                if (interviewDTO.getApplicationId() != null) {
                    existingInterview.setApplicationId(interviewDTO.getApplicationId());
                }
                if (interviewDTO.getScheduledDateTime() != null) {
                    existingInterview.setScheduledDateTime(interviewDTO.getScheduledDateTime());
                }
                if (interviewDTO.getLocation() != null) {
                    existingInterview.setLocation(interviewDTO.getLocation());
                }
                if (interviewDTO.getType() != null) {
                    existingInterview.setType(interviewDTO.getType());
                }
                if (interviewDTO.getStatus() != null) {
                    existingInterview.setStatus(interviewDTO.getStatus());
                }
                if (interviewDTO.getConsultantId() != null) {
                    existingInterview.setConsultantId(interviewDTO.getConsultantId());
                }
                if (interviewDTO.getNotes() != null) {
                    existingInterview.setNotes(interviewDTO.getNotes());
                }
                existingInterview.setUpdatedAt(Instant.now());
                return existingInterview;
            })
            .map(interviewRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<InterviewDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Interviews");
        return interviewRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewDTO> findAllByApplicationId(Long applicationId) {
        log.debug("Request to get all Interviews for Application ID: {}", applicationId);
        return interviewRepository.findByApplicationId(applicationId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InterviewDTO> findAllByConsultantId(Long consultantId) {
        log.debug("Request to get all Interviews for Consultant ID: {}", consultantId);
        return interviewRepository.findByConsultantId(consultantId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InterviewDTO> findOne(Long id) {
        log.debug("Request to get Interview : {}", id);
        return interviewRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Interview : {}", id);
        interviewRepository.deleteById(id);
    }

    private InterviewDTO toDto(Interview interview) {
        InterviewDTO dto = new InterviewDTO();
        dto.setId(interview.getId());
        dto.setApplicationId(interview.getApplicationId());
        dto.setConsultantId(interview.getConsultantId());
        dto.setScheduledDateTime(interview.getScheduledDateTime());
        dto.setDurationMinutes(interview.getDurationMinutes());
        dto.setType(interview.getType());
        dto.setStatus(interview.getStatus());
        dto.setLocation(interview.getLocation());
        dto.setMeetingLink(interview.getMeetingLink());
        dto.setNotes(interview.getNotes());
        dto.setCreatedAt(interview.getCreatedAt());
        dto.setUpdatedAt(interview.getUpdatedAt());
        return dto;
    }

    private Interview toEntity(InterviewDTO dto) {
        // Utiliser le constructeur sans argument pour éviter les erreurs de compilation
        Interview entity = new Interview();
        entity.setId(dto.getId());
        entity.setApplicationId(dto.getApplicationId());
        entity.setConsultantId(dto.getConsultantId());
        entity.setScheduledDateTime(dto.getScheduledDateTime());
        entity.setDurationMinutes(dto.getDurationMinutes());
        entity.setType(dto.getType());
        entity.setStatus(dto.getStatus());
        entity.setLocation(dto.getLocation());
        entity.setMeetingLink(dto.getMeetingLink());
        entity.setNotes(dto.getNotes());
        // createdAt and updatedAt are handled in save/update logic
        return entity;
    }
}

