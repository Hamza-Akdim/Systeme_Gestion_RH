package com.talentwave.repository.offre;

import com.talentwave.domain.offer.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Long>, JpaSpecificationExecutor<JobOffer> {
    // Example: Find offers by status
    List<JobOffer> findByStatus(JobOffer.OfferStatus status);

    // Example: Find offers by job profile id
//    List<JobOffer> findByJobProfileId(Long jobProfileId);
}

