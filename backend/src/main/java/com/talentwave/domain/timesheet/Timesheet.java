package com.talentwave.domain.timesheet;

import com.talentwave.domain.User; // Assuming User (Consultant) submits the timesheet
import com.talentwave.domain.mission.Mission; // Optional: Link timesheet entry to a specific mission
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "timesheet")
@Getter
@Setter
@NoArgsConstructor
public class Timesheet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private User consultant;

    @NotNull
    private LocalDate workDate;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false) // Assuming hours worked must be positive
    private BigDecimal hoursWorked;

    @Lob
    private String description; // Description of work done

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mission_id") // Optional: if timesheet is for a specific mission
    private Mission mission;

    @NotNull
    @Enumerated(EnumType.STRING)
    private TimesheetStatus status = TimesheetStatus.SUBMITTED;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_user_id") // User who approved the timesheet (e.g., Manager)
    private User approvedBy;

    private LocalDate approvalDate;

    public enum TimesheetStatus {
        SUBMITTED,
        APPROVED,
        REJECTED,
        PENDING_CORRECTION
    }

    public Timesheet(User consultant, LocalDate workDate, BigDecimal hoursWorked, Mission mission) {
        this.consultant = consultant;
        this.workDate = workDate;
        this.hoursWorked = hoursWorked;
        this.mission = mission;
    }
}

