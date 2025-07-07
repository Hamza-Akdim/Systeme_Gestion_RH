package com.talentwave.service.sourcing;

import com.talentwave.service.dto.sourcing.SourcingStatDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SourcingStatService {

    /**
     * Save a sourcingStat.
     *
     * @param sourcingStatDTO the entity to save.
     * @return the persisted entity.
     */
    SourcingStatDTO save(SourcingStatDTO sourcingStatDTO);

    /**
     * Partially updates a sourcingStat.
     *
     * @param sourcingStatDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SourcingStatDTO> partialUpdate(SourcingStatDTO sourcingStatDTO);

    /**
     * Get all the sourcingStats.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SourcingStatDTO> findAll(Pageable pageable);

    /**
     * Get all the sourcingStats for a specific job offer.
     *
     * @param jobOfferId the id of the job offer.
     * @return the list of entities.
     */
    List<SourcingStatDTO> findAllByJobOfferId(Long jobOfferId);

    /**
     * Get the "id" sourcingStat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SourcingStatDTO> findOne(Long id);

    /**
     * Delete the "id" sourcingStat.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

