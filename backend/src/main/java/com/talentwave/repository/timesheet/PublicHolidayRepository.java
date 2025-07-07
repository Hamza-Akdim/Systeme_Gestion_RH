package com.talentwave.repository.timesheet;

import com.talentwave.domain.timesheet.PublicHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository pour gérer les jours fériés.
 */
@Repository
public interface PublicHolidayRepository extends JpaRepository<PublicHoliday, Long> {
    
    /**
     * Trouve tous les jours fériés entre deux dates, triés par date croissante.
     *
     * @param startDate la date de début
     * @param endDate la date de fin
     * @return la liste des jours fériés
     */
    List<PublicHoliday> findAllByDateBetweenOrderByDateAsc(LocalDate startDate, LocalDate endDate);
    
    /**
     * Trouve tous les jours fériés pour un pays donné.
     *
     * @param countryCode le code du pays
     * @return la liste des jours fériés
     */
    List<PublicHoliday> findAllByCountryCode(String countryCode);
    
    /**
     * Vérifie si une date est un jour férié.
     *
     * @param date la date à vérifier
     * @return true si la date est un jour férié, false sinon
     */
    boolean existsByDate(LocalDate date);
}
