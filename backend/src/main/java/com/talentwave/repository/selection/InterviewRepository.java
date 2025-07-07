package com.talentwave.repository.selection;

import com.talentwave.domain.selection.Interview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long>, JpaSpecificationExecutor<Interview> {
    List<Interview> findByApplicationId(Long applicationId);
    List<Interview> findByConsultantId(Long consultantId);
    List<Interview> findByScheduledDateTimeBetween(LocalDateTime startTime, LocalDateTime endTime);
    List<Interview> findByStatus(String status);
}

