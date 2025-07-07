package com.talentwave.domain.selection;

import com.talentwave.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Entité représentant un feedback dans le processus de sélection.
 */
@Entity
@Table(name = "feedbacks")
@Getter
@Setter
@NoArgsConstructor
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "interview_id")
    private Long interviewId;
    
    @ManyToOne
    @JoinColumn(name = "interview_id", insertable = false, updatable = false)
    private Interview interview;

    @Column(name = "reviewer_id")
    private Long reviewerId;
    
    @ManyToOne
    @JoinColumn(name = "reviewer_id", insertable = false, updatable = false)
    private User reviewer;
    
    @Column(name = "reviewer_name")
    private String reviewerName;

    @Column(name = "pros", columnDefinition = "TEXT")
    private String pros;

    @Column(name = "cons", columnDefinition = "TEXT")
    private String cons;
    
    @Column(name = "comments", columnDefinition = "TEXT")
    private String comments;

    @Column(name = "recommendation", columnDefinition = "TEXT")
    private String recommendation;

    @Column(name = "rating")
    private Integer rating;

    @Column(name = "created_at")
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    // Getters et setters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getInterviewId() {
        return interviewId;
    }
    
    public void setInterviewId(Long interviewId) {
        this.interviewId = interviewId;
    }
    
    public Interview getInterview() {
        return interview;
    }
    
    public void setInterview(Interview interview) {
        this.interview = interview;
        if (interview != null) {
            this.interviewId = interview.getId();
        }
    }
    
    public Long getReviewerId() {
        return reviewerId;
    }
    
    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }
    
    public User getReviewer() {
        return reviewer;
    }
    
    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
        if (reviewer != null) {
            this.reviewerId = reviewer.getId();
        }
    }
    
    public String getReviewerName() {
        return reviewerName;
    }
    
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
    
    public String getPros() {
        return pros;
    }
    
    public void setPros(String pros) {
        this.pros = pros;
    }
    
    public String getCons() {
        return cons;
    }
    
    public void setCons(String cons) {
        this.cons = cons;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
    }
    
    public String getRecommendation() {
        return recommendation;
    }
    
    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
