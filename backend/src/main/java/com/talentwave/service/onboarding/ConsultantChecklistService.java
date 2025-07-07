package com.talentwave.service.onboarding;

import com.talentwave.service.dto.onboarding.ConsultantChecklistDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ConsultantChecklistService {

    /**
     * Save a consultantChecklist.
     *
     * @param consultantChecklistDTO the entity to save.
     * @return the persisted entity.
     */
    ConsultantChecklistDTO save(ConsultantChecklistDTO consultantChecklistDTO);

    /**
     * Partially updates a consultantChecklist.
     *
     * @param consultantChecklistDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ConsultantChecklistDTO> partialUpdate(ConsultantChecklistDTO consultantChecklistDTO);

    /**
     * Get all the consultantChecklists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ConsultantChecklistDTO> findAll(Pageable pageable);

    /**
     * Get all the consultantChecklists for a specific consultant.
     *
     * @param consultantId the id of the consultant.
     * @return the list of entities.
     */
    List<ConsultantChecklistDTO> findAllByConsultantId(Long consultantId);

    /**
     * Get the "id" consultantChecklist.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ConsultantChecklistDTO> findOne(Long id);

    /**
     * Delete the "id" consultantChecklist.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

