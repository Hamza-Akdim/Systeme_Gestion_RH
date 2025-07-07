package com.talentwave.service.hr;

import com.talentwave.service.dto.hr.AnnualExecutionPlanDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AnnualExecutionPlanService {

    /**
     * Save a annualExecutionPlan.
     *
     * @param annualExecutionPlanDTO the entity to save.
     * @return the persisted entity.
     */
    AnnualExecutionPlanDTO save(AnnualExecutionPlanDTO annualExecutionPlanDTO);

    /**
     * Partially updates a annualExecutionPlan.
     *
     * @param annualExecutionPlanDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AnnualExecutionPlanDTO> partialUpdate(AnnualExecutionPlanDTO annualExecutionPlanDTO);

    /**
     * Get all the annualExecutionPlans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AnnualExecutionPlanDTO> findAll(Pageable pageable);

    /**
     * Get the "id" annualExecutionPlan.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AnnualExecutionPlanDTO> findOne(Long id);

    /**
     * Delete the "id" annualExecutionPlan.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

