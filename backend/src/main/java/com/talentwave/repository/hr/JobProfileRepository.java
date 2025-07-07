package com.talentwave.repository.hr;

import com.talentwave.domain.hr.JobProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobProfileRepository extends JpaRepository<JobProfile, Long> {
    // Add custom query methods if needed
}

