package com.talentwave.repository.candidate;

import com.talentwave.domain.candidate.Application;
import com.talentwave.domain.candidate.Application.ApplicationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long>, JpaSpecificationExecutor<Application> {
    List<Application> findByCandidateId(Long candidateId);
    List<Application> findByJobOfferId(Long jobOfferId);
    List<Application> findByCandidateIdAndJobOfferId(Long candidateId, Long jobOfferId);
    List<Application> findByStatus(ApplicationStatus status);
    
    // Méthodes avec pagination
    Page<Application> findByCandidateId(Long candidateId, Pageable pageable);
    Page<Application> findByJobOfferId(Long jobOfferId, Pageable pageable);
}

