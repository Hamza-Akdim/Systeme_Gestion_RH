package com.talentwave.repository.onboarding;

import com.talentwave.domain.onboarding.ConsultantChecklist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConsultantChecklistRepository extends JpaRepository<ConsultantChecklist, Long> {

    List<ConsultantChecklist> findByConsultant_Id(Long consultantId);

    List<ConsultantChecklist> findByOnboardingStep_Id(Long onboardingStepId);

    List<ConsultantChecklist> findByConsultant_IdAndOnboardingStep_Id(Long consultantId, Long onboardingStepId);

    List<ConsultantChecklist> findByConsultant_IdAndCompleted(Long consultantId, boolean completed);
}

