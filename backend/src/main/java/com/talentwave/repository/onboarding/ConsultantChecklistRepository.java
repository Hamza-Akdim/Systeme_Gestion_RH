package com.talentwave.repository.onboarding;

import com.talentwave.domain.onboarding.ConsultantChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultantChecklistRepository extends JpaRepository<ConsultantChecklist, Long> {
    List<ConsultantChecklist> findByConsultantId(Long consultantId);
    List<ConsultantChecklist> findByOnboardingStepId(Long onboardingStepId);
    List<ConsultantChecklist> findByConsultantIdAndOnboardingStepId(Long consultantId, Long onboardingStepId);
    List<ConsultantChecklist> findByConsultantIdAndCompleted(Long consultantId, boolean completed);
}

