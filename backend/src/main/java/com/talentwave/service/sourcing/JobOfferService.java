package com.talentwave.service.sourcing;

import com.talentwave.service.dto.sourcing.JobOfferDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface JobOfferService {

    /**
     * Save a jobOffer.
     *
     * @param jobOfferDTO the entity to save.
     * @return the persisted entity.
     */
    JobOfferDTO save(JobOfferDTO jobOfferDTO);

    /**
     * Partially updates a jobOffer.
     *
     * @param jobOfferDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JobOfferDTO> partialUpdate(JobOfferDTO jobOfferDTO);

    /**
     * Get all the jobOffers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<JobOfferDTO> findAll(Pageable pageable);

    /**
     * Get the "id" jobOffer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<JobOfferDTO> findOne(Long id);

    /**
     * Delete the "id" jobOffer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

