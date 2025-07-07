package com.talentwave.domain.evaluation;

import com.talentwave.domain.User; // Assuming User (Consultant) is being reviewed, and another User (Manager) is the reviewer
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "annual_review")
@Getter
@Setter
@NoArgsConstructor
public class AnnualReview {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private User consultant;

    @NotNull
    private Integer reviewYear;

    @NotNull
    private LocalDate reviewDate;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reviewer_id") // Manager or HR conducting the review
    private User reviewer;

    @NotNull
    @Min(1) // Overall performance rating, e.g., 1 to 5
    @Max(5)
    private Integer overallPerformanceRating;

    @Lob
    private String strengths;

    @Lob
    private String areasForImprovement;

    @Lob
    private String goalsForNextYear;

    @Lob
    private String consultantComments;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    public AnnualReview(User consultant, Integer reviewYear, LocalDate reviewDate, User reviewer, Integer overallPerformanceRating) {
        this.consultant = consultant;
        this.reviewYear = reviewYear;
        this.reviewDate = reviewDate;
        this.reviewer = reviewer;
        this.overallPerformanceRating = overallPerformanceRating;
    }
    
    // Getters explicites pour résoudre les problèmes de compilation
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getConsultant() {
        return consultant;
    }
    
    public void setConsultant(User consultant) {
        this.consultant = consultant;
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
    
    public User getReviewer() {
        return reviewer;
    }
    
    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
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

