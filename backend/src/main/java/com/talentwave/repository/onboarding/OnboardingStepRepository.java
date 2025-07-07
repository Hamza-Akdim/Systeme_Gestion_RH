package com.talentwave.repository.onboarding;

import com.talentwave.domain.onboarding.OnboardingStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OnboardingStepRepository extends JpaRepository<OnboardingStep, Long> {
    List<OnboardingStep> findAllByOrderByStepOrderAsc();
    
    // Ajout de la méthode manquante pour résoudre l'erreur de compilation
    List<OnboardingStep> findByConsultantIdOrderByStepOrderAsc(Long consultantId);
}

