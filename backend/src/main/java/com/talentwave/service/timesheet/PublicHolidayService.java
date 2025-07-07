package com.talentwave.service.timesheet;

import com.talentwave.service.dto.timesheet.PublicHolidayDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PublicHolidayService {

    /**
     * Save a publicHoliday.
     *
     * @param publicHolidayDTO the entity to save.
     * @return the persisted entity.
     */
    PublicHolidayDTO save(PublicHolidayDTO publicHolidayDTO);

    /**
     * Partially updates a publicHoliday.
     *
     * @param publicHolidayDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PublicHolidayDTO> partialUpdate(PublicHolidayDTO publicHolidayDTO);

    /**
     * Get all the publicHolidays.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PublicHolidayDTO> findAll(Pageable pageable);

    /**
     * Get all public holidays within a date range.
     *
     * @param startDate the start date of the range.
     * @param endDate the end date of the range.
     * @return the list of public holidays.
     */
    List<PublicHolidayDTO> findAllByDateBetween(LocalDate startDate, LocalDate endDate);

    /**
     * Get the "id" publicHoliday.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PublicHolidayDTO> findOne(Long id);

    /**
     * Delete the "id" publicHoliday.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}

