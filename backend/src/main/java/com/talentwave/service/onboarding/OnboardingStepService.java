package com.talentwave.service.onboarding;

import com.talentwave.service.dto.onboarding.OnboardingStepDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface OnboardingStepService {

    /**
     * Save an onboardingStep.
     *
     * @param onboardingStepDTO the entity to save.
     * @return the persisted entity.
     */
    OnboardingStepDTO save(OnboardingStepDTO onboardingStepDTO);

    /**
     * Partially updates an onboardingStep.
     *
     * @param onboardingStepDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OnboardingStepDTO> partialUpdate(OnboardingStepDTO onboardingStepDTO);

    /**
     * Get all the onboardingSteps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OnboardingStepDTO> findAll(Pageable pageable);

    /**
     * Get all the onboardingSteps for a specific consultant.
     *
     * @param consultantId the id of the consultant.
     * @return the list of entities.
     */
    List<OnboardingStepDTO> findAllByConsultantId(Long consultantId);

    /**
     * Get the "id" onboardingStep.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OnboardingStepDTO> findOne(Long id);

    /**
     * Delete the "id" onboardingStep.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

