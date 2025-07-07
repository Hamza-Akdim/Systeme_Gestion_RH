package com.talentwave.service.candidate;

import com.talentwave.service.dto.candidate.CandidateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CandidateService {

    /**
     * Save a candidate.
     *
     * @param candidateDTO the entity to save.
     * @return the persisted entity.
     */
    CandidateDTO save(CandidateDTO candidateDTO);

    /**
     * Partially updates a candidate.
     *
     * @param candidateDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<CandidateDTO> partialUpdate(CandidateDTO candidateDTO);

    /**
     * Get all the candidates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CandidateDTO> findAll(Pageable pageable);

    /**
     * Get the "id" candidate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CandidateDTO> findOne(Long id);

    /**
     * Delete the "id" candidate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Find candidate by email.
     *
     * @param email the email.
     * @return the optional candidate DTO.
     */
    Optional<CandidateDTO> findByEmail(String email);
}

