package com.talentwave.service.timesheet;

import com.talentwave.service.dto.timesheet.TimesheetDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TimesheetService {

    /**
     * Save a timesheet.
     *
     * @param timesheetDTO the entity to save.
     * @return the persisted entity.
     */
    TimesheetDTO save(TimesheetDTO timesheetDTO);

    /**
     * Partially updates a timesheet.
     *
     * @param timesheetDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TimesheetDTO> partialUpdate(TimesheetDTO timesheetDTO);

    /**
     * Get all the timesheets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TimesheetDTO> findAll(Pageable pageable);

    /**
     * Get all the timesheets for a specific consultant.
     *
     * @param consultantId the id of the consultant.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TimesheetDTO> findAllByConsultantId(Long consultantId, Pageable pageable);

    /**
     * Get all the timesheets for a specific mission.
     *
     * @param missionId the id of the mission.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TimesheetDTO> findAllByMissionId(Long missionId, Pageable pageable);
    
    /**
     * Get all timesheets for a consultant within a date range.
     *
     * @param consultantId the consultant ID.
     * @param startDate the start date.
     * @param endDate the end date.
     * @return the list of timesheets.
     */
    List<TimesheetDTO> findAllByConsultantIdAndWorkDateBetween(Long consultantId, LocalDate startDate, LocalDate endDate);


    /**
     * Get the "id" timesheet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TimesheetDTO> findOne(Long id);

    /**
     * Delete the "id" timesheet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * Get all timesheets for the current user.
     * Méthode de compatibilité pour les contrôleurs existants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TimesheetDTO> findAllForCurrentUser(Pageable pageable);
}

