package com.talentwave.service.mission;

import com.talentwave.service.dto.mission.AssignmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AssignmentService {

    /**
     * Save an assignment.
     *
     * @param assignmentDTO the entity to save.
     * @return the persisted entity.
     */
    AssignmentDTO save(AssignmentDTO assignmentDTO);

    /**
     * Partially updates an assignment.
     *
     * @param assignmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AssignmentDTO> partialUpdate(AssignmentDTO assignmentDTO);

    /**
     * Get all the assignments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssignmentDTO> findAll(Pageable pageable);

    /**
     * Get all the assignments for a specific mission.
     *
     * @param missionId the id of the mission.
     * @return the list of entities.
     */
    List<AssignmentDTO> findAllByMissionId(Long missionId);

    /**
     * Get all the assignments for a specific consultant.
     *
     * @param consultantId the id of the consultant.
     * @return the list of entities.
     */
    List<AssignmentDTO> findAllByConsultantId(Long consultantId);

    /**
     * Get the "id" assignment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssignmentDTO> findOne(Long id);

    /**
     * Delete the "id" assignment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

