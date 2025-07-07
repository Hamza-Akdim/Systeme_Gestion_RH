package com.talentwave.repository.candidate;

import com.talentwave.domain.candidate.Candidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {
    Optional<Candidate> findByEmail(String email);
    boolean existsByEmail(String email);
    // Add other custom query methods if needed, e.g., findByLastName, findBySkills, etc.
}

