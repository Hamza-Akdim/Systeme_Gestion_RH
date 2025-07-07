package com.talentwave.repository.evaluation;

import com.talentwave.domain.evaluation.SkillAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SkillAssessmentRepository extends JpaRepository<SkillAssessment, Long> {
    List<SkillAssessment> findByConsultantId(Long consultantId);
    List<SkillAssessment> findBySkillNameContainingIgnoreCase(String skillName);
    List<SkillAssessment> findByConsultantIdAndAssessmentDateBetween(Long consultantId, LocalDate startDate, LocalDate endDate);
    List<SkillAssessment> findByAssessorId(Long assessorId);
}

