package com.talentwave.service.evaluation;

import com.talentwave.service.dto.evaluation.SkillAssessmentDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SkillAssessmentService {

    /**
     * Save a skillAssessment.
     *
     * @param skillAssessmentDTO the entity to save.
     * @return the persisted entity.
     */
    SkillAssessmentDTO save(SkillAssessmentDTO skillAssessmentDTO);

    /**
     * Partially updates a skillAssessment.
     *
     * @param skillAssessmentDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SkillAssessmentDTO> partialUpdate(SkillAssessmentDTO skillAssessmentDTO);

    /**
     * Get all the skillAssessments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SkillAssessmentDTO> findAll(Pageable pageable);

    /**
     * Get all the skillAssessments for a specific consultant.
     *
     * @param consultantId the id of the consultant.
     * @return the list of entities.
     */
    List<SkillAssessmentDTO> findAllByConsultantId(Long consultantId);

    /**
     * Get the "id" skillAssessment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SkillAssessmentDTO> findOne(Long id);

    /**
     * Delete the "id" skillAssessment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

