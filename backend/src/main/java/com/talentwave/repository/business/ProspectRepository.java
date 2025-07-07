package com.talentwave.repository.business;

import com.talentwave.domain.business.Prospect;
import com.talentwave.domain.business.Prospect.ProspectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProspectRepository extends JpaRepository<Prospect, Long>, JpaSpecificationExecutor<Prospect> {
    List<Prospect> findByCompanyNameContainingIgnoreCase(String companyName);
    List<Prospect> findByStatus(String status);
    List<Prospect> findByResponsibleUserId(Long responsibleUserId);
}

