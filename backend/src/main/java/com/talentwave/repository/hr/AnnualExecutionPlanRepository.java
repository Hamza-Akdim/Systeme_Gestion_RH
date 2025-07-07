package com.talentwave.repository.hr;

import com.talentwave.domain.hr.AnnualExecutionPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnualExecutionPlanRepository extends JpaRepository<AnnualExecutionPlan, Long> {
    // Add custom query methods if needed, for example, findByYear
    // Optional<AnnualExecutionPlan> findByYear(Integer year);
}

