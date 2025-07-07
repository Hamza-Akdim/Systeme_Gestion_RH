package com.talentwave.web.rest.candidate;

import com.talentwave.service.candidate.CandidateService;
import com.talentwave.service.dto.candidate.CandidateDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/candidates")
public class CandidateController {

    private final Logger log = LoggerFactory.getLogger(CandidateController.class);

    private final CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    /**
     * {@code POST  /api/candidates} : Create a new candidate.
     *
     * @param candidateDTO the candidateDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new candidateDTO, or with status {@code 400 (Bad Request)} if the candidate has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<CandidateDTO> createCandidate(@Valid @RequestBody CandidateDTO candidateDTO) {
        log.debug("REST request to save Candidate : {}", candidateDTO);
        if (candidateDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        CandidateDTO result = candidateService.save(candidateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/candidates/:id} : Updates an existing candidate.
     *
     * @param id the id of the candidateDTO to save.
     * @param candidateDTO the candidateDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidateDTO,
     * or with status {@code 400 (Bad Request)} if the candidateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the candidateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the candidateDTO couldn\"t be updated.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<CandidateDTO> updateCandidate(@PathVariable Long id, @Valid @RequestBody CandidateDTO candidateDTO) {
        log.debug("REST request to update Candidate : {}, {}", id, candidateDTO);
        if (candidateDTO.getId() == null || !candidateDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<CandidateDTO> existingCandidate = candidateService.findOne(id);
        if (existingCandidate.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        CandidateDTO result = candidateService.save(candidateDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/candidates/:id} : Partially updates an existing candidate.
     *
     * @param id the id of the candidateDTO to save.
     * @param candidateDTO the candidateDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated candidateDTO,
     * or with status {@code 400 (Bad Request)} if the candidateDTO is not valid,
     * or with status {@code 404 (Not Found)} if the candidateDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the candidateDTO couldn\"t be updated.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<CandidateDTO> partialUpdateCandidate(@PathVariable Long id, @RequestBody CandidateDTO candidateDTO) {
        log.debug("REST request to partially update Candidate : {}, {}", id, candidateDTO);
        if (candidateDTO.getId() == null || !candidateDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<CandidateDTO> updatedCandidate = candidateService.partialUpdate(candidateDTO);
        return updatedCandidate.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/candidates} : get all the candidates.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of candidates in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<List<CandidateDTO>> getAllCandidates(Pageable pageable) {
        log.debug("REST request to get a page of Candidates");
        Page<CandidateDTO> page = candidateService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/candidates/:id} : get the "id" candidate.
     *
     * @param id the id of the candidateDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the candidateDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<CandidateDTO> getCandidate(@PathVariable Long id) {
        log.debug("REST request to get Candidate : {}", id);
        Optional<CandidateDTO> candidateDTO = candidateService.findOne(id);
        return candidateDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/candidates/:id} : delete the "id" candidate.
     *
     * @param id the id of the candidateDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<Void> deleteCandidate(@PathVariable Long id) {
        log.debug("REST request to delete Candidate : {}", id);
        candidateService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

