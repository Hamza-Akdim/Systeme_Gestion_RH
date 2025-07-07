package com.talentwave.web.rest.timesheet;

import com.talentwave.service.dto.timesheet.PublicHolidayDTO;
import com.talentwave.service.timesheet.PublicHolidayService;
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
@RequestMapping("/api/holidays") // Endpoint for PublicHoliday as per initial request
public class PublicHolidayController {

    private final Logger log = LoggerFactory.getLogger(PublicHolidayController.class);

    private final PublicHolidayService publicHolidayService;

    public PublicHolidayController(PublicHolidayService publicHolidayService) {
        this.publicHolidayService = publicHolidayService;
    }

    /**
     * {@code POST  /api/holidays} : Create a new publicHoliday.
     *
     * @param publicHolidayDTO the publicHolidayDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new publicHolidayDTO, or with status {@code 400 (Bad Request)} if the publicHoliday has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<PublicHolidayDTO> createPublicHoliday(@Valid @RequestBody PublicHolidayDTO publicHolidayDTO) {
        log.debug("REST request to save PublicHoliday : {}", publicHolidayDTO);
        if (publicHolidayDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        PublicHolidayDTO result = publicHolidayService.save(publicHolidayDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/holidays/:id} : Updates an existing publicHoliday.
     *
     * @param id the id of the publicHolidayDTO to save.
     * @param publicHolidayDTO the publicHolidayDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicHolidayDTO,
     * or with status {@code 400 (Bad Request)} if the publicHolidayDTO is not valid,
     * or with status {@code 404 (Not Found)} if the publicHolidayDTO is not found.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<PublicHolidayDTO> updatePublicHoliday(@PathVariable Long id, @Valid @RequestBody PublicHolidayDTO publicHolidayDTO) {
        log.debug("REST request to update PublicHoliday : {}, {}", id, publicHolidayDTO);
        if (publicHolidayDTO.getId() == null || !publicHolidayDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<PublicHolidayDTO> existingHoliday = publicHolidayService.findOne(id);
        if (existingHoliday.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        PublicHolidayDTO result = publicHolidayService.save(publicHolidayDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/holidays/:id} : Partially updates an existing publicHoliday.
     *
     * @param id the id of the publicHolidayDTO to save.
     * @param publicHolidayDTO the publicHolidayDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated publicHolidayDTO,
     * or with status {@code 400 (Bad Request)} if the publicHolidayDTO is not valid,
     * or with status {@code 404 (Not Found)} if the publicHolidayDTO is not found.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<PublicHolidayDTO> partialUpdatePublicHoliday(@PathVariable Long id, @RequestBody PublicHolidayDTO publicHolidayDTO) {
        log.debug("REST request to partially update PublicHoliday : {}, {}", id, publicHolidayDTO);
        if (publicHolidayDTO.getId() == null || !publicHolidayDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<PublicHolidayDTO> updatedHoliday = publicHolidayService.partialUpdate(publicHolidayDTO);
        return updatedHoliday.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/holidays} : get all the publicHolidays.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of publicHolidays in body.
     */
    @GetMapping
    @PreAuthorize("permitAll()") // Public holidays are generally public information
    public ResponseEntity<List<PublicHolidayDTO>> getAllPublicHolidays(Pageable pageable) {
        log.debug("REST request to get a page of PublicHolidays");
        Page<PublicHolidayDTO> page = publicHolidayService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/holidays/:id} : get the "id" publicHoliday.
     *
     * @param id the id of the publicHolidayDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the publicHolidayDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("permitAll()")
    public ResponseEntity<PublicHolidayDTO> getPublicHoliday(@PathVariable Long id) {
        log.debug("REST request to get PublicHoliday : {}", id);
        Optional<PublicHolidayDTO> publicHolidayDTO = publicHolidayService.findOne(id);
        return publicHolidayDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/holidays/:id} : delete the "id" publicHoliday.
     *
     * @param id the id of the publicHolidayDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<Void> deletePublicHoliday(@PathVariable Long id) {
        log.debug("REST request to delete PublicHoliday : {}", id);
        publicHolidayService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

