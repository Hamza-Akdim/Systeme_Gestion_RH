package com.talentwave.repository.selection;

import com.talentwave.domain.selection.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    List<Feedback> findByInterviewId(Long interviewId);
    List<Feedback> findByReviewerId(Long reviewerId);
    List<Feedback> findByInterviewApplicationId(Long applicationId);
}

