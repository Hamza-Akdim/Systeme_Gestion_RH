package com.talentwave.service.mission;

import com.talentwave.service.dto.mission.MissionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface MissionService {

    /**
     * Save a mission.
     *
     * @param missionDTO the entity to save.
     * @return the persisted entity.
     */
    MissionDTO save(MissionDTO missionDTO);

    /**
     * Partially updates a mission.
     *
     * @param missionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<MissionDTO> partialUpdate(MissionDTO missionDTO);

    /**
     * Get all the missions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MissionDTO> findAll(Pageable pageable);

    /**
     * Get all the missions for a specific client.
     *
     * @param clientId the id of the client.
     * @return the list of entities.
     */
    List<MissionDTO> findAllByClientId(Long clientId);

    /**
     * Get the "id" mission.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MissionDTO> findOne(Long id);

    /**
     * Delete the "id" mission.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

