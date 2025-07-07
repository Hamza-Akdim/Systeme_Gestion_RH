package com.talentwave.web.rest.business;

import com.talentwave.service.business.ProspectService;
import com.talentwave.service.dto.business.ProspectDTO;
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
@RequestMapping("/api/business/prospects")
public class ProspectController {

    private final Logger log = LoggerFactory.getLogger(ProspectController.class);

    private final ProspectService prospectService;

    public ProspectController(ProspectService prospectService) {
        this.prospectService = prospectService;
    }

    /**
     * {@code POST  /api/business/prospects} : Create a new prospect.
     *
     * @param prospectDTO the prospectDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new prospectDTO, or with status {@code 400 (Bad Request)} if the prospect has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')") // Or specific business role
    public ResponseEntity<ProspectDTO> createProspect(@Valid @RequestBody ProspectDTO prospectDTO) {
        log.debug("REST request to save Prospect : {}", prospectDTO);
        if (prospectDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        ProspectDTO result = prospectService.save(prospectDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/business/prospects/:id} : Updates an existing prospect.
     *
     * @param id the id of the prospectDTO to save.
     * @param prospectDTO the prospectDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prospectDTO,
     * or with status {@code 400 (Bad Request)} if the prospectDTO is not valid,
     * or with status {@code 404 (Not Found)} if the prospectDTO is not found.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')") // Or specific business role
    public ResponseEntity<ProspectDTO> updateProspect(@PathVariable Long id, @Valid @RequestBody ProspectDTO prospectDTO) {
        log.debug("REST request to update Prospect : {}, {}", id, prospectDTO);
        if (prospectDTO.getId() == null || !prospectDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<ProspectDTO> existingProspect = prospectService.findOne(id);
        if (existingProspect.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        ProspectDTO result = prospectService.save(prospectDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/business/prospects/:id} : Partially updates an existing prospect.
     *
     * @param id the id of the prospectDTO to save.
     * @param prospectDTO the prospectDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated prospectDTO,
     * or with status {@code 400 (Bad Request)} if the prospectDTO is not valid,
     * or with status {@code 404 (Not Found)} if the prospectDTO is not found.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')") // Or specific business role
    public ResponseEntity<ProspectDTO> partialUpdateProspect(@PathVariable Long id, @RequestBody ProspectDTO prospectDTO) {
        log.debug("REST request to partially update Prospect : {}, {}", id, prospectDTO);
        if (prospectDTO.getId() == null || !prospectDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<ProspectDTO> updatedProspect = prospectService.partialUpdate(prospectDTO);
        return updatedProspect.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/business/prospects} : get all the prospects.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of prospects in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')") // Or specific business role
    public ResponseEntity<List<ProspectDTO>> getAllProspects(Pageable pageable) {
        log.debug("REST request to get a page of Prospects");
        Page<ProspectDTO> page = prospectService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/business/prospects/:id} : get the "id" prospect.
     *
     * @param id the id of the prospectDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the prospectDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')") // Or specific business role
    public ResponseEntity<ProspectDTO> getProspect(@PathVariable Long id) {
        log.debug("REST request to get Prospect : {}", id);
        Optional<ProspectDTO> prospectDTO = prospectService.findOne(id);
        return prospectDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/business/prospects/:id} : delete the "id" prospect.
     *
     * @param id the id of the prospectDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')") // Or specific business role
    public ResponseEntity<Void> deleteProspect(@PathVariable Long id) {
        log.debug("REST request to delete Prospect : {}", id);
        prospectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

