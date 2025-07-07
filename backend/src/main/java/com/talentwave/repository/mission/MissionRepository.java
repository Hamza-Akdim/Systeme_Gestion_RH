package com.talentwave.repository.mission;

import com.talentwave.domain.mission.Mission;
import com.talentwave.domain.mission.Mission.MissionStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MissionRepository extends JpaRepository<Mission, Long>, JpaSpecificationExecutor<Mission> {
    List<Mission> findByClientNameContainingIgnoreCase(String clientName);
    List<Mission> findByStatus(MissionStatus status);
    List<Mission> findByProjectManagerId(Long projectManagerId);
    List<Mission> findByStartDateBetween(LocalDate start, LocalDate end);
    
    // Méthode ajoutée pour correspondre aux appels dans le service
    List<Mission> findByClientId(Long clientId);
}

