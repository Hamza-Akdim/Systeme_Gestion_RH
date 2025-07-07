package com.talentwave.service.dto.evaluation;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class AnnualReviewDTO {
    private Long id;

    @NotNull
    private Long consultantId;
    private String consultantName; // Optional: for display

    @NotNull
    private Integer reviewYear;

    @NotNull
    private LocalDate reviewDate;

    @NotNull
    private Long reviewerId; // User ID of the reviewer
    private String reviewerName; // Optional: for display

    @NotNull
    @Min(1)
    @Max(5)
    private Integer overallPerformanceRating;

    private String strengths;

    private String areasForImprovement;

    private String goalsForNextYear;

    private String consultantComments;

    private Instant createdAt;

    private Instant updatedAt;
    
    // Getters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getConsultantId() {
        return consultantId;
    }
    
    public void setConsultantId(Long consultantId) {
        this.consultantId = consultantId;
    }
    
    public String getConsultantName() {
        return consultantName;
    }
    
    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }
    
    public Integer getReviewYear() {
        return reviewYear;
    }
    
    public void setReviewYear(Integer reviewYear) {
        this.reviewYear = reviewYear;
    }
    
    public LocalDate getReviewDate() {
        return reviewDate;
    }
    
    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
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
    
    public Integer getOverallPerformanceRating() {
        return overallPerformanceRating;
    }
    
    public void setOverallPerformanceRating(Integer overallPerformanceRating) {
        this.overallPerformanceRating = overallPerformanceRating;
    }
    
    public String getStrengths() {
        return strengths;
    }
    
    public void setStrengths(String strengths) {
        this.strengths = strengths;
    }
    
    public String getAreasForImprovement() {
        return areasForImprovement;
    }
    
    public void setAreasForImprovement(String areasForImprovement) {
        this.areasForImprovement = areasForImprovement;
    }
    
    public String getGoalsForNextYear() {
        return goalsForNextYear;
    }
    
    public void setGoalsForNextYear(String goalsForNextYear) {
        this.goalsForNextYear = goalsForNextYear;
    }
    
    public String getConsultantComments() {
        return consultantComments;
    }
    
    public void setConsultantComments(String consultantComments) {
        this.consultantComments = consultantComments;
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

