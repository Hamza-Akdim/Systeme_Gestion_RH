package com.talentwave.web.rest.mission;

import com.talentwave.service.dto.mission.MissionDTO;
import com.talentwave.service.mission.MissionService;
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
@RequestMapping("/api/missions")
public class MissionController {

    private final Logger log = LoggerFactory.getLogger(MissionController.class);

    private final MissionService missionService;

    public MissionController(MissionService missionService) {
        this.missionService = missionService;
    }

    /**
     * {@code POST  /api/missions} : Create a new mission.
     *
     * @param missionDTO the missionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new missionDTO, or with status {@code 400 (Bad Request)} if the mission has already an ID.
     */
    @PostMapping
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<MissionDTO> createMission(@Valid @RequestBody MissionDTO missionDTO) {
        log.debug("REST request to save Mission : {}", missionDTO);
        if (missionDTO.getId() != null) {
            return ResponseEntity.badRequest().build();
        }
        MissionDTO result = missionService.save(missionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    /**
     * {@code PUT  /api/missions/:id} : Updates an existing mission.
     *
     * @param id the id of the missionDTO to save.
     * @param missionDTO the missionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated missionDTO,
     * or with status {@code 400 (Bad Request)} if the missionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the missionDTO is not found.
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<MissionDTO> updateMission(@PathVariable Long id, @Valid @RequestBody MissionDTO missionDTO) {
        log.debug("REST request to update Mission : {}, {}", id, missionDTO);
        if (missionDTO.getId() == null || !missionDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<MissionDTO> existingMission = missionService.findOne(id);
        if (existingMission.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        MissionDTO result = missionService.save(missionDTO);
        return ResponseEntity.ok().body(result);
    }

    /**
     * {@code PATCH  /api/missions/:id} : Partially updates an existing mission.
     *
     * @param id the id of the missionDTO to save.
     * @param missionDTO the missionDTO to update partially.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated missionDTO,
     * or with status {@code 400 (Bad Request)} if the missionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the missionDTO is not found.
     */
    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<MissionDTO> partialUpdateMission(@PathVariable Long id, @RequestBody MissionDTO missionDTO) {
        log.debug("REST request to partially update Mission : {}, {}", id, missionDTO);
        if (missionDTO.getId() == null || !missionDTO.getId().equals(id)) {
            return ResponseEntity.badRequest().build();
        }
        Optional<MissionDTO> updatedMission = missionService.partialUpdate(missionDTO);
        return updatedMission.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code GET  /api/missions} : get all the missions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of missions in body.
     */
    @GetMapping
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or hasAuthority(\'CONSULTANT\")")
    public ResponseEntity<List<MissionDTO>> getAllMissions(Pageable pageable) {
        log.debug("REST request to get a page of Missions");
        Page<MissionDTO> page = missionService.findAll(pageable);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /api/missions/:id} : get the "id" mission.
     *
     * @param id the id of the missionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the missionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\") or @missionService.isParticipantInMission(principal, #id)") // Assuming a method to check if user can access
    public ResponseEntity<MissionDTO> getMission(@PathVariable Long id) {
        log.debug("REST request to get Mission : {}", id);
        Optional<MissionDTO> missionDTO = missionService.findOne(id);
        return missionDTO.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * {@code DELETE  /api/missions/:id} : delete the "id" mission.
     *
     * @param id the id of the missionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority(\'ADMIN\") or hasAuthority(\'HR\")")
    public ResponseEntity<Void> deleteMission(@PathVariable Long id) {
        log.debug("REST request to delete Mission : {}", id);
        missionService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

