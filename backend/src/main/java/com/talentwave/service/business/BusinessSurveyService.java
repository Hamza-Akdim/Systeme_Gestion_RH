package com.talentwave.service.business;

import com.talentwave.service.dto.business.BusinessSurveyDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BusinessSurveyService {

    /**
     * Save a businessSurvey.
     *
     * @param businessSurveyDTO the entity to save.
     * @return the persisted entity.
     */
    BusinessSurveyDTO save(BusinessSurveyDTO businessSurveyDTO);

    /**
     * Partially updates a businessSurvey.
     *
     * @param businessSurveyDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<BusinessSurveyDTO> partialUpdate(BusinessSurveyDTO businessSurveyDTO);

    /**
     * Get all the businessSurveys.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<BusinessSurveyDTO> findAll(Pageable pageable);

    /**
     * Get all the businessSurveys for a specific prospect.
     *
     * @param prospectId the id of the prospect.
     * @return the list of entities.
     */
    List<BusinessSurveyDTO> findAllByProspectId(Long prospectId);

    /**
     * Get the "id" businessSurvey.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<BusinessSurveyDTO> findOne(Long id);

    /**
     * Delete the "id" businessSurvey.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

