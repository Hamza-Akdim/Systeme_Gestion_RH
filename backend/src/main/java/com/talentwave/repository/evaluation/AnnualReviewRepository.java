package com.talentwave.repository.evaluation;

import com.talentwave.domain.evaluation.AnnualReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnualReviewRepository extends JpaRepository<AnnualReview, Long> {
    List<AnnualReview> findByConsultantId(Long consultantId);
    List<AnnualReview> findByReviewYear(Integer reviewYear);
    List<AnnualReview> findByConsultantIdAndReviewYear(Long consultantId, Integer reviewYear);
    List<AnnualReview> findByReviewerId(Long reviewerId);
}

