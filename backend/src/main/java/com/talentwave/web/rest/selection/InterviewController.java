package com.talentwave.web.rest.selection;

import com.talentwave.service.selection.InterviewService;
import com.talentwave.service.dto.selection.InterviewDTO;
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
@RequestMapping("/api/selection/interviews")
public class InterviewController {

    private final Logger log = LoggerFactory.getLogger(InterviewController.class);

    private final InterviewService interviewService;

    public InterviewController(InterviewService interviewService) {
        this.interviewService = interviewService;
    }

    /**
     * {@code POST  /api/selection/interviews} : Create a new interview.
     *
     * @param interviewDTO the interviewDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new interviewDTO, or with status {@code 400 (Bad Request)} if the interview has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<InterviewDTO> createInterview(@Valid @RequestBody InterviewDTO interviewDTO) {
        log.debug("REST request to save Interview : {}", interviewDTO);
        if (interviewDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        InterviewDTO result = interviewService.save(interviewDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/selection/interviews/:id} : Updates an existing interview.
     *
     * @param id the id of the interviewDTO to save.
     * @param interviewDTO the interviewDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewDTO,
     * or with status {@code 400 (Bad Request)} if the interviewDTO is not valid,
     * or with status {@code 404 (Not Found)} if the interviewDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the interviewDTO couldn\"t be updated.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<InterviewDTO> updateInterview(@PathVariable Long id, @Valid @RequestBody InterviewDTO interviewDTO) {
        log.debug("REST request to update Interview : {}, {}", id, interviewDTO);
        if (interviewDTO.getId() == null || !interviewDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<InterviewDTO> existingInterview = interviewService.findOne(id);
        if (existingInterview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        InterviewDTO result = interviewService.save(interviewDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/selection/interviews/:id} : Partially updates an existing interview.
     *
     * @param id the id of the interviewDTO to save.
     * @param interviewDTO the interviewDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated interviewDTO,
     * or with status {@code 400 (Bad Request)} if the interviewDTO is not valid,
     * or with status {@code 404 (Not Found)} if the interviewDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the interviewDTO couldn\"t be updated.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<InterviewDTO> partialUpdateInterview(@PathVariable Long id, @RequestBody InterviewDTO interviewDTO) {
        log.debug("REST request to partially update Interview : {}, {}", id, interviewDTO);
        if (interviewDTO.getId() == null || !interviewDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<InterviewDTO> updatedInterview = interviewService.partialUpdate(interviewDTO);
        return updatedInterview.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/selection/interviews} : get all the interviews.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviews in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or hasAuthority(\'CONSULTANT\')")
    public ResponseEntity<List<InterviewDTO>> getAllInterviews(Pageable pageable) {
        log.debug("REST request to get a page of Interviews");
        Page<InterviewDTO> page = interviewService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/selection/interviews/application/:applicationId} : get all interviews for a specific application.
     *
     * @param applicationId the id of the application.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviews in body.
     */
    @GetMapping("/application/{applicationId}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or @interviewService.isParticipantInApplicationInterviews(principal, #applicationId)")
    public ResponseEntity<List<InterviewDTO>> getInterviewsByApplication(@PathVariable Long applicationId) {
        log.debug("REST request to get Interviews for Application ID: {}", applicationId);
        List<InterviewDTO> interviews = interviewService.findAllByApplicationId(applicationId);
        return ResponseEntity.ok().body(interviews);
    }
    
    /**
     * {@code GET  /api/selection/interviews/consultant/:consultantId} : get all interviews for a specific consultant (as interviewer or candidate).
     *
     * @param consultantId the id of the consultant.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of interviews in body.
     */
    @GetMapping("/consultant/{consultantId}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or principal.username == @userService.findById(#consultantId).orElse(null)?.username")
    public ResponseEntity<List<InterviewDTO>> getInterviewsByConsultant(@PathVariable Long consultantId) {
        log.debug("REST request to get Interviews for Consultant ID: {}", consultantId);
        List<InterviewDTO> interviews = interviewService.findAllByConsultantId(consultantId);
        return ResponseEntity.ok().body(interviews);
    }


    /**
     * {@code GET  /api/selection/interviews/:id} : get the "id" interview.
     *
     * @param id the id of the interviewDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the interviewDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\') or @interviewService.isParticipantInInterview(principal, #id)")
    public ResponseEntity<InterviewDTO> getInterview(@PathVariable Long id) {
        log.debug("REST request to get Interview : {}", id);
        Optional<InterviewDTO> interviewDTO = interviewService.findOne(id);
        return interviewDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/selection/interviews/:id} : delete the "id" interview.
     *
     * @param id the id of the interviewDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\') or hasAuthority(\'HR\')")
    public ResponseEntity<Void> deleteInterview(@PathVariable Long id) {
        log.debug("REST request to delete Interview : {}", id);
        interviewService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

