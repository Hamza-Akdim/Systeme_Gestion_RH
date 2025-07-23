package com.talentwave.service.impl.timesheet;

import com.talentwave.service.dto.timesheet.TimesheetDTO;
import com.talentwave.service.timesheet.TimesheetService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TimesheetServiceImpl implements TimesheetService {

    @Override
    public TimesheetDTO save(TimesheetDTO timesheetDTO) {
        return null;
    }

    @Override
    public Optional<TimesheetDTO> partialUpdate(TimesheetDTO timesheetDTO) {
        return Optional.empty();
    }

    @Override
    public Page<TimesheetDTO> findAll(Pageable pageable) {
        return Page.empty();
    }

    @Override
    public Page<TimesheetDTO> findAllByConsultantId(Long consultantId, Pageable pageable) {
        return Page.empty();
    }

    @Override
    public Page<TimesheetDTO> findAllByMissionId(Long missionId, Pageable pageable) {
        return Page.empty();
    }

    @Override
    public List<TimesheetDTO> findAllByConsultantIdAndWorkDateBetween(Long consultantId, LocalDate startDate, LocalDate endDate) {
        return List.of();
    }

    @Override
    public Optional<TimesheetDTO> findOne(Long id) {
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        // Do nothing
    }

    @Override
    public Page<TimesheetDTO> findAllForCurrentUser(Pageable pageable) {
        return Page.empty();
    }
}
