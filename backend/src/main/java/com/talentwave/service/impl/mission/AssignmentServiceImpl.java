package com.talentwave.service.impl.mission;

import com.talentwave.domain.User;
import com.talentwave.domain.mission.Assignment;
import com.talentwave.domain.mission.Mission;
import com.talentwave.repository.UserRepository;
import com.talentwave.repository.mission.AssignmentRepository;
import com.talentwave.repository.mission.MissionRepository;
import com.talentwave.service.dto.mission.AssignmentDTO;
import com.talentwave.service.mission.AssignmentService;
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
import java.util.stream.StreamSupport;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final Logger log = LoggerFactory.getLogger(AssignmentServiceImpl.class);

    private final AssignmentRepository assignmentRepository;
    private final MissionRepository missionRepository;
    private final UserRepository userRepository;

    public AssignmentServiceImpl(
            AssignmentRepository assignmentRepository,
            MissionRepository missionRepository,
            UserRepository userRepository) {
        this.assignmentRepository = assignmentRepository;
        this.missionRepository = missionRepository;
        this.userRepository = userRepository;
    }

    public AssignmentDTO save(AssignmentDTO assignmentDTO) {
        log.debug("Request to save Assignment : {}", assignmentDTO);
        Assignment assignment = toEntity(assignmentDTO);
        if (assignment.getId() == null) {
            assignment.setCreatedAt(Instant.now());
        }
        assignment.setUpdatedAt(Instant.now());
        assignment = assignmentRepository.save(assignment);
        return toDto(assignment);
    }

    public Optional<AssignmentDTO> partialUpdate(AssignmentDTO assignmentDTO) {
        log.debug("Request to partially update Assignment : {}", assignmentDTO);

        return assignmentRepository
                .findById(assignmentDTO.getId())
                .map(existingAssignment -> {
                    if (assignmentDTO.getMissionId() != null) {
                        Mission mission = missionRepository.findById(assignmentDTO.getMissionId())
                                .orElseThrow(() -> new RuntimeException("Mission not found with id: " + assignmentDTO.getMissionId()));
                        existingAssignment.setMission(mission);
                    }
                    if (assignmentDTO.getConsultantId() != null) {
                        com.talentwave.domain.User consultant = userRepository.findById(assignmentDTO.getConsultantId())
                                .orElseThrow(() -> new RuntimeException("Consultant not found with id: " + assignmentDTO.getConsultantId()));
                        existingAssignment.setConsultant(consultant);
                    }
                    if (assignmentDTO.getStartDate() != null) {
                        existingAssignment.setStartDate(assignmentDTO.getStartDate());
                    }
                    if (assignmentDTO.getEndDate() != null) {
                        existingAssignment.setEndDate(assignmentDTO.getEndDate());
                    }
                    if (assignmentDTO.getStatus() != null) {
                        existingAssignment.setStatus(assignmentDTO.getStatus());
                    }
                    if (assignmentDTO.getRoleInMission() != null) {
                        existingAssignment.setRoleInMission(assignmentDTO.getRoleInMission());
                    }

                    existingAssignment.setUpdatedAt(Instant.now());
                    return existingAssignment;
                })
                .map(assignmentRepository::save)
                .map(this::toDto);
    }

    @Transactional(readOnly = true)
    public Page<AssignmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Assignments");
        return assignmentRepository.findAll(pageable).map(this::toDto);
    }

    @Transactional(readOnly = true)
    public List<AssignmentDTO> findAllByMissionId(Long missionId) {
        log.debug("Request to get all Assignments for mission: {}", missionId);
        return assignmentRepository.findByMissionId(missionId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public List<AssignmentDTO> findAllByConsultantId(Long consultantId) {
        log.debug("Request to get all Assignments for consultant: {}", consultantId);
        return assignmentRepository.findByConsultantId(consultantId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Optional<AssignmentDTO> findOne(Long id) {
        log.debug("Request to get Assignment : {}", id);
        return assignmentRepository.findById(id).map(this::toDto);
    }

    public void delete(Long id) {
        log.debug("Request to delete Assignment : {}", id);
        assignmentRepository.deleteById(id);
    }

    private AssignmentDTO toDto(Assignment assignment) {
        AssignmentDTO dto = new AssignmentDTO();
        dto.setId(assignment.getId());
        dto.setMissionId(assignment.getMission().getId());
        dto.setMissionTitle(assignment.getMission().getTitle());
        dto.setConsultantId(assignment.getConsultant().getId());
        // Assuming User has a name field or method to get full name
        dto.setConsultantName(assignment.getConsultant().getUsername());
        dto.setStartDate(assignment.getStartDate());
        dto.setEndDate(assignment.getEndDate());
        dto.setStatus(assignment.getStatus());
        dto.setRoleInMission(assignment.getRoleInMission());
        dto.setCreatedAt(assignment.getCreatedAt());
        dto.setUpdatedAt(assignment.getUpdatedAt());
        return dto;
    }

    private Assignment toEntity(AssignmentDTO dto) {
        // Utiliser le constructeur sans argument pour éviter les erreurs de compilation
        Assignment entity = new Assignment();
        
        if (dto.getMissionId() != null) {
            Mission mission = missionRepository.findById(dto.getMissionId())
                .orElseThrow(() -> new IllegalArgumentException("Mission not found"));
            entity.setMission(mission);
        }
        
        if (dto.getConsultantId() != null) {
            com.talentwave.domain.User consultant = userRepository.findById(dto.getConsultantId())
                .orElseThrow(() -> new IllegalArgumentException("Consultant not found"));
            entity.setConsultant(consultant);
        }
        
        entity.setId(dto.getId());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setStatus(dto.getStatus());
        entity.setRoleInMission(dto.getRoleInMission());
        
        return entity;
    }
}