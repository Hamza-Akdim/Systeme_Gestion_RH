package com.talentwave.service.business;

import com.talentwave.service.dto.business.ProspectDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ProspectService {

    /**
     * Save a prospect.
     *
     * @param prospectDTO the entity to save.
     * @return the persisted entity.
     */
    ProspectDTO save(ProspectDTO prospectDTO);

    /**
     * Partially updates a prospect.
     *
     * @param prospectDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ProspectDTO> partialUpdate(ProspectDTO prospectDTO);

    /**
     * Get all the prospects.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ProspectDTO> findAll(Pageable pageable);

    /**
     * Get all the prospects assigned to a specific user.
     *
     * @param responsibleUserId the id of the responsible user.
     * @return the list of entities.
     */
    List<ProspectDTO> findAllByResponsibleUserId(Long responsibleUserId);

    /**
     * Get the "id" prospect.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ProspectDTO> findOne(Long id);

    /**
     * Delete the "id" prospect.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

