package com.talentwave.repository.timesheet;

import com.talentwave.domain.timesheet.LeaveRequest;
import com.talentwave.domain.timesheet.LeaveRequest.LeaveStatus;
import com.talentwave.domain.timesheet.LeaveRequest.LeaveType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long>, JpaSpecificationExecutor<LeaveRequest> {
    List<LeaveRequest> findByConsultantId(Long consultantId);
    List<LeaveRequest> findByConsultantIdAndStartDateBetween(Long consultantId, LocalDate start, LocalDate end);
    List<LeaveRequest> findByStatus(LeaveStatus status);
    List<LeaveRequest> findByLeaveType(LeaveType leaveType);
    List<LeaveRequest> findByApprovedById(Long approvedById);
}

