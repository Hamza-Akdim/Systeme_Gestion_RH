package com.talentwave.domain.onboarding;

import com.talentwave.domain.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Entité représentant une étape d'intégration (onboarding) d'un consultant.
 */
@Entity
@Table(name = "onboarding_steps")
@Getter
@Setter
@NoArgsConstructor
public class OnboardingStep {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consultant_id")
    private User consultant;

    @Column(name = "step_name", nullable = false)
    private String stepName;
    
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "due_date")
    private LocalDate dueDate;

    @Column(name = "completed")
    private boolean completed;

    @Column(name = "completion_date")
    private LocalDate completionDate;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "order_index")
    private Integer orderIndex;
    
    @Column(name = "step_order")
    private Integer stepOrder;
    
    @Column(name = "mandatory")
    private boolean mandatory;
    
    @Column(name = "created_at")
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;

    /**
     * Constructeur avec paramètres.
     *
     * @param consultant le consultant concerné
     * @param stepName le nom de l'étape
     * @param description la description de l'étape
     * @param dueDate la date d'échéance
     * @param orderIndex l'ordre de l'étape
     */
    public OnboardingStep(User consultant, String stepName, String description, LocalDate dueDate, Integer orderIndex) {
        this.consultant = consultant;
        this.stepName = stepName;
        this.name = stepName;
        this.description = description;
        this.dueDate = dueDate;
        this.completed = false;
        this.orderIndex = orderIndex;
        this.stepOrder = orderIndex;
        this.mandatory = true;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
    
    // Getters et setters explicites pour résoudre les problèmes de compilation
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
    
    public String getStepName() {
        return stepName;
    }
    
    public void setStepName(String stepName) {
        this.stepName = stepName;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public LocalDate getCompletionDate() {
        return completionDate;
    }
    
    public void setCompletionDate(LocalDate completionDate) {
        this.completionDate = completionDate;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public Integer getOrderIndex() {
        return orderIndex;
    }
    
    public void setOrderIndex(Integer orderIndex) {
        this.orderIndex = orderIndex;
    }
    
    public Integer getStepOrder() {
        return stepOrder;
    }
    
    public void setStepOrder(Integer stepOrder) {
        this.stepOrder = stepOrder;
    }
    
    public boolean isMandatory() {
        return mandatory;
    }
    
    public void setMandatory(boolean mandatory) {
        this.mandatory = mandatory;
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
