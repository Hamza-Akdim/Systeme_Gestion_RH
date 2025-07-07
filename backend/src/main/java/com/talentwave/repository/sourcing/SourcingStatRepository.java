package com.talentwave.repository.sourcing;

import com.talentwave.domain.sourcing.SourcingStat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface SourcingStatRepository extends JpaRepository<SourcingStat, Long> {
    List<SourcingStat> findByJobOfferId(Long jobOfferId);
    List<SourcingStat> findByStatDate(LocalDate statDate);
    List<SourcingStat> findByJobOfferIdAndStatDateBetween(Long jobOfferId, LocalDate startDate, LocalDate endDate);
}

