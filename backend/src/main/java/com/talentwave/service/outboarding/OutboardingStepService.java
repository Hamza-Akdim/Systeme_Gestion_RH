package com.talentwave.service.outboarding;

import com.talentwave.service.dto.outboarding.OutboardingStepDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OutboardingStepService {

    /**
     * Save an outboardingStep.
     *
     * @param outboardingStepDTO the entity to save.
     * @return the persisted entity.
     */
    OutboardingStepDTO save(OutboardingStepDTO outboardingStepDTO);

    /**
     * Partially updates an outboardingStep.
     *
     * @param outboardingStepDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OutboardingStepDTO> partialUpdate(OutboardingStepDTO outboardingStepDTO);

    /**
     * Get all the outboardingSteps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OutboardingStepDTO> findAll(Pageable pageable);

    /**
     * Get all the outboardingSteps for a specific consultant.
     *
     * @param consultantId the id of the consultant.
     * @return the list of entities.
     */
    List<OutboardingStepDTO> findAllByConsultantId(Long consultantId);

    /**
     * Get the "id" outboardingStep.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OutboardingStepDTO> findOne(Long id);

    /**
     * Delete the "id" outboardingStep.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

