package com.talentwave.web.rest.business;

import com.talentwave.service.business.BusinessSurveyService;
import com.talentwave.service.dto.business.BusinessSurveyDTO;
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
@RequestMapping("/api/business/surveys") // Endpoint for BusinessSurvey
public class BusinessSurveyController {

    private final Logger log = LoggerFactory.getLogger(BusinessSurveyController.class);

    private final BusinessSurveyService businessSurveyService;

    public BusinessSurveyController(BusinessSurveyService businessSurveyService) {
        this.businessSurveyService = businessSurveyService;
    }

    /**
     * {@code POST  /api/business/surveys} : Create a new businessSurvey.
     *
     * @param businessSurveyDTO the businessSurveyDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new businessSurveyDTO, or with status {@code 400 (Bad Request)} if the businessSurvey has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')") // Or specific business role
    public ResponseEntity<BusinessSurveyDTO> createBusinessSurvey(@Valid @RequestBody BusinessSurveyDTO businessSurveyDTO) {
        log.debug("REST request to save BusinessSurvey : {}", businessSurveyDTO);
        if (businessSurveyDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        BusinessSurveyDTO result = businessSurveyService.save(businessSurveyDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/business/surveys/:id} : Updates an existing businessSurvey.
     *
     * @param id the id of the businessSurveyDTO to save.
     * @param businessSurveyDTO the businessSurveyDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessSurveyDTO,
     * or with status {@code 400 (Bad Request)} if the businessSurveyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the businessSurveyDTO is not found.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')") // Or specific business role
    public ResponseEntity<BusinessSurveyDTO> updateBusinessSurvey(@PathVariable Long id, @Valid @RequestBody BusinessSurveyDTO businessSurveyDTO) {
        log.debug("REST request to update BusinessSurvey : {}, {}", id, businessSurveyDTO);
        if (businessSurveyDTO.getId() == null || !businessSurveyDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<BusinessSurveyDTO> existingSurvey = businessSurveyService.findOne(id);
        if (existingSurvey.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        BusinessSurveyDTO result = businessSurveyService.save(businessSurveyDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/business/surveys/:id} : Partially updates an existing businessSurvey.
     *
     * @param id the id of the businessSurveyDTO to save.
     * @param businessSurveyDTO the businessSurveyDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated businessSurveyDTO,
     * or with status {@code 400 (Bad Request)} if the businessSurveyDTO is not valid,
     * or with status {@code 404 (Not Found)} if the businessSurveyDTO is not found.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')") // Or specific business role
    public ResponseEntity<BusinessSurveyDTO> partialUpdateBusinessSurvey(@PathVariable Long id, @RequestBody BusinessSurveyDTO businessSurveyDTO) {
        log.debug("REST request to partially update BusinessSurvey : {}, {}", id, businessSurveyDTO);
        if (businessSurveyDTO.getId() == null || !businessSurveyDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<BusinessSurveyDTO> updatedSurvey = businessSurveyService.partialUpdate(businessSurveyDTO);
        return updatedSurvey.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/business/surveys} : get all the businessSurveys.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of businessSurveys in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')") // Or specific business role
    public ResponseEntity<List<BusinessSurveyDTO>> getAllBusinessSurveys(Pageable pageable) {
        log.debug("REST request to get a page of BusinessSurveys");
        Page<BusinessSurveyDTO> page = businessSurveyService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/business/surveys/:id} : get the "id" businessSurvey.
     *
     * @param id the id of the businessSurveyDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the businessSurveyDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')") // Or specific business role
    public ResponseEntity<BusinessSurveyDTO> getBusinessSurvey(@PathVariable Long id) {
        log.debug("REST request to get BusinessSurvey : {}", id);
        Optional<BusinessSurveyDTO> businessSurveyDTO = businessSurveyService.findOne(id);
        return businessSurveyDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/business/surveys/:id} : delete the "id" businessSurvey.
     *
     * @param id the id of the businessSurveyDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')") // Or specific business role
    public ResponseEntity<Void> deleteBusinessSurvey(@PathVariable Long id) {
        log.debug("REST request to delete BusinessSurvey : {}", id);
        businessSurveyService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

