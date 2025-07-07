package com.talentwave.web.rest.sourcing;

import com.talentwave.service.sourcing.SourcingStatService;
import com.talentwave.service.dto.sourcing.SourcingStatDTO;
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
@RequestMapping("/api/sourcing/stats") // Assuming this is the endpoint for SourcingStat
public class SourcingStatController {

    private final Logger log = LoggerFactory.getLogger(SourcingStatController.class);

    private final SourcingStatService sourcingStatService;

    public SourcingStatController(SourcingStatService sourcingStatService) {
        this.sourcingStatService = sourcingStatService;
    }

    /**
     * {@code POST  /api/sourcing/stats} : Create a new sourcingStat.
     *
     * @param sourcingStatDTO the sourcingStatDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new sourcingStatDTO, or with status {@code 400 (Bad Request)} if the sourcingStat has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<SourcingStatDTO> createSourcingStat(@Valid @RequestBody SourcingStatDTO sourcingStatDTO) {
        log.debug("REST request to save SourcingStat : {}", sourcingStatDTO);
        if (sourcingStatDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        SourcingStatDTO result = sourcingStatService.save(sourcingStatDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/sourcing/stats/:id} : Updates an existing sourcingStat.
     *
     * @param id the id of the sourcingStatDTO to save.
     * @param sourcingStatDTO the sourcingStatDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sourcingStatDTO,
     * or with status {@code 400 (Bad Request)} if the sourcingStatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sourcingStatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sourcingStatDTO couldn\"t be updated.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<SourcingStatDTO> updateSourcingStat(@PathVariable Long id, @Valid @RequestBody SourcingStatDTO sourcingStatDTO) {
        log.debug("REST request to update SourcingStat : {}, {}", id, sourcingStatDTO);
        if (sourcingStatDTO.getId() == null || !sourcingStatDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<SourcingStatDTO> existingStat = sourcingStatService.findOne(id);
        if (existingStat.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        SourcingStatDTO result = sourcingStatService.save(sourcingStatDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/sourcing/stats/:id} : Partially updates an existing sourcingStat.
     *
     * @param id the id of the sourcingStatDTO to save.
     * @param sourcingStatDTO the sourcingStatDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated sourcingStatDTO,
     * or with status {@code 400 (Bad Request)} if the sourcingStatDTO is not valid,
     * or with status {@code 404 (Not Found)} if the sourcingStatDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the sourcingStatDTO couldn\"t be updated.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<SourcingStatDTO> partialUpdateSourcingStat(@PathVariable Long id, @RequestBody SourcingStatDTO sourcingStatDTO) {
        log.debug("REST request to partially update SourcingStat : {}, {}", id, sourcingStatDTO);
        if (sourcingStatDTO.getId() == null || !sourcingStatDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<SourcingStatDTO> updatedStat = sourcingStatService.partialUpdate(sourcingStatDTO);
        return updatedStat.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/sourcing/stats} : get all the sourcingStats.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sourcingStats in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<List<SourcingStatDTO>> getAllSourcingStats(Pageable pageable) {
        log.debug("REST request to get a page of SourcingStats");
        Page<SourcingStatDTO> page = sourcingStatService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/sourcing/stats/:id} : get the "id" sourcingStat.
     *
     * @param id the id of the sourcingStatDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the sourcingStatDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<SourcingStatDTO> getSourcingStat(@PathVariable Long id) {
        log.debug("REST request to get SourcingStat : {}", id);
        Optional<SourcingStatDTO> sourcingStatDTO = sourcingStatService.findOne(id);
        return sourcingStatDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/sourcing/stats/:id} : delete the "id" sourcingStat.
     *
     * @param id the id of the sourcingStatDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<Void> deleteSourcingStat(@PathVariable Long id) {
        log.debug("REST request to delete SourcingStat : {}", id);
        sourcingStatService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

