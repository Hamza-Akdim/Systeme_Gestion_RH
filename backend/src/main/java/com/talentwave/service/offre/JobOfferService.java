package com.talentwave.service.offre;

import com.talentwave.service.dto.offre.JobOfferDTO;
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
    JobOfferDTO saveOffer(JobOfferDTO jobOfferDTO);

    /**
     * Partially updates a jobOffer.
     *
     * @param jobOfferDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<JobOfferDTO> partialUpdate(JobOfferDTO jobOfferDTO);

    /**
     * Get all the jobOffers.
     * @param page
     * @param size
     * @return the entity.
     */
    Page<JobOfferDTO> findAll(int page, int size);

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

