package com.talentwave.service.timesheet;

import com.talentwave.service.dto.timesheet.LeaveRequestDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface LeaveRequestService {

    /**
     * Save a leaveRequest.
     *
     * @param leaveRequestDTO the entity to save.
     * @return the persisted entity.
     */
    LeaveRequestDTO save(LeaveRequestDTO leaveRequestDTO);

    /**
     * Partially updates a leaveRequest.
     *
     * @param leaveRequestDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<LeaveRequestDTO> partialUpdate(LeaveRequestDTO leaveRequestDTO);

    /**
     * Get all the leaveRequests.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LeaveRequestDTO> findAll(Pageable pageable);

    /**
     * Get all the leaveRequests for a specific consultant.
     *
     * @param consultantId the id of the consultant.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LeaveRequestDTO> findAllByConsultantId(Long consultantId, Pageable pageable);

    /**
     * Get all leave requests for a consultant within a date range.
     *
     * @param consultantId the consultant ID.
     * @param startDate the start date of the range.
     * @param endDate the end date of the range.
     * @return the list of leave requests.
     */
    List<LeaveRequestDTO> findAllByConsultantIdAndDateRange(Long consultantId, LocalDate startDate, LocalDate endDate);

    /**
     * Get the "id" leaveRequest.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<LeaveRequestDTO> findOne(Long id);

    /**
     * Delete the "id" leaveRequest.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
    
    /**
     * Get all leave requests for the current user or all users if admin.
     * Méthode de compatibilité pour les contrôleurs existants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<LeaveRequestDTO> findAllForCurrentUserOrAll(Pageable pageable);
}

