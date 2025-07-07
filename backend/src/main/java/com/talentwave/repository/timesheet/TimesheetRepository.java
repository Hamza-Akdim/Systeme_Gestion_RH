package com.talentwave.repository.timesheet;

import com.talentwave.domain.timesheet.Timesheet;
import com.talentwave.domain.timesheet.Timesheet.TimesheetStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface TimesheetRepository extends JpaRepository<Timesheet, Long>, JpaSpecificationExecutor<Timesheet> {
    List<Timesheet> findByConsultantId(Long consultantId);
    List<Timesheet> findByConsultantIdAndWorkDateBetween(Long consultantId, LocalDate startDate, LocalDate endDate);
    List<Timesheet> findByMissionId(Long missionId);
    List<Timesheet> findByStatus(TimesheetStatus status);
    List<Timesheet> findByConsultantIdAndStatus(Long consultantId, TimesheetStatus status);
}

