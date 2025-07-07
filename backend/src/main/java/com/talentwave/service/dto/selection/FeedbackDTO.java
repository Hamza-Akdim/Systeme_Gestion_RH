package com.talentwave.service.dto.selection;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
public class FeedbackDTO {
    private Long id;

    @NotNull
    private Long interviewId;

    @NotNull
    private Long reviewerId; // User ID of the reviewer (interviewer or other)
    private String reviewerName; // Optional: for display

    @NotNull
    @Min(1) // Rating scale, e.g., 1 to 5 or 1 to 10
    @Max(5) // Assuming a 1-5 scale for now
    private Integer rating;

    private String comments;

    private String pros;

    private String cons;

    private String recommendation; // e.g., HIRE, NO_HIRE, FURTHER_INTERVIEW

    private Instant createdAt;

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
    
    public Long getReviewerId() {
        return reviewerId;
    }
    
    public void setReviewerId(Long reviewerId) {
        this.reviewerId = reviewerId;
    }
    
    public String getReviewerName() {
        return reviewerName;
    }
    
    public void setReviewerName(String reviewerName) {
        this.reviewerName = reviewerName;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getComments() {
        return comments;
    }
    
    public void setComments(String comments) {
        this.comments = comments;
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
    
    public String getRecommendation() {
        return recommendation;
    }
    
    public void setRecommendation(String recommendation) {
        this.recommendation = recommendation;
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

