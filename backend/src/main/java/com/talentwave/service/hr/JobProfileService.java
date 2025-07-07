package com.talentwave.service.hr;

import com.talentwave.service.dto.hr.JobProfileDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JobProfileService {

    /**
     * Save a jobProfile.
     *
     * @param jobProfileDTO the entity to save.
     * @return the persisted entity.
     */
    JobProfileDTO save(JobProfileDTO jobProfileDTO);

    /**
     * Partially updates a jobProfile.
     *
     * @param jobProfileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JobProfileDTO> partialUpdate(JobProfileDTO jobProfileDTO);

    /**
     * Get all the jobProfiles.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JobProfileDTO> findAll(Pageable pageable);

    /**
     * Get the "id" jobProfile.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobProfileDTO> findOne(Long id);

    /**
     * Delete the "id" jobProfile.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

