package com.talentwave.repository.business;

import com.talentwave.domain.business.BusinessSurvey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BusinessSurveyRepository extends JpaRepository<BusinessSurvey, Long> {
    List<BusinessSurvey> findByProspectId(Long prospectId);
    List<BusinessSurvey> findBySurveyDateBetween(LocalDate startDate, LocalDate endDate);
    List<BusinessSurvey> findByConductedById(Long userId);
}

