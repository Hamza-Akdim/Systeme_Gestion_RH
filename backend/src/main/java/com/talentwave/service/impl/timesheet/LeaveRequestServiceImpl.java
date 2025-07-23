package com.talentwave.service.impl.timesheet;

import com.talentwave.service.dto.timesheet.LeaveRequestDTO;
import com.talentwave.service.timesheet.LeaveRequestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LeaveRequestServiceImpl implements LeaveRequestService {

    @Override
    public LeaveRequestDTO save(LeaveRequestDTO leaveRequestDTO) {
        // implementation here
        return null;
    }

    @Override
    public Optional<LeaveRequestDTO> partialUpdate(LeaveRequestDTO leaveRequestDTO) {
        // implementation here
        return Optional.empty();
    }

    @Override
    public Page<LeaveRequestDTO> findAll(Pageable pageable) {
        // implementation here
        return Page.empty();
    }

    @Override
    public Page<LeaveRequestDTO> findAllByConsultantId(Long consultantId, Pageable pageable) {
        // implementation here
        return Page.empty();
    }

    @Override
    public List<LeaveRequestDTO> findAllByConsultantIdAndDateRange(Long consultantId, LocalDate startDate, LocalDate endDate) {
        // implementation here
        return List.of();
    }

    @Override
    public Optional<LeaveRequestDTO> findOne(Long id) {
        // implementation here
        return Optional.empty();
    }

    @Override
    public void delete(Long id) {
        // implementation here
    }

    @Override
    public Page<LeaveRequestDTO> findAllForCurrentUserOrAll(Pageable pageable) {
        // implementation here
        return Page.empty();
    }
}
