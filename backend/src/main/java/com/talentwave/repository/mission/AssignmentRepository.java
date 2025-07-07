package com.talentwave.repository.mission;

import com.talentwave.domain.mission.Assignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByMissionId(Long missionId);
    List<Assignment> findByConsultantId(Long consultantId);
    List<Assignment> findByMissionIdAndConsultantId(Long missionId, Long consultantId);
    List<Assignment> findByConsultantIdAndStatus(Long consultantId, Assignment.AssignmentStatus status);
    
    // Méthodes ajoutées pour la pagination
    Page<Assignment> findAllByConsultantId(Long consultantId, Pageable pageable);
    Page<Assignment> findAllByMissionId(Long missionId, Pageable pageable);
    
    // Méthodes renommées pour correspondre aux appels dans le service
    List<Assignment> findAllByConsultantId(Long consultantId);
    List<Assignment> findAllByMissionId(Long missionId);
}

