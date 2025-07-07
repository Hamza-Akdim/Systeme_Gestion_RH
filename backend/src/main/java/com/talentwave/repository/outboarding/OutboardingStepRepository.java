package com.talentwave.repository.outboarding;

import com.talentwave.domain.outboarding.OutboardingStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OutboardingStepRepository extends JpaRepository<OutboardingStep, Long> {
    List<OutboardingStep> findByConsultantIdOrderByStepOrderAsc(Long consultantId);
    List<OutboardingStep> findByConsultantIdAndCompleted(Long consultantId, boolean completed);
}

