package com.talentwave.domain.timesheet;

import com.talentwave.domain.User; // Assuming User (Consultant) requests the leave
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "leave_request")
@Getter
@Setter
@NoArgsConstructor
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consultant_id")
    private User consultant;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LeaveType leaveType; // e.g., ANNUAL_LEAVE, SICK_LEAVE, UNPAID_LEAVE

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent
    private LocalDate endDate;

    @Lob
    private String reason;

    @NotNull
    @Enumerated(EnumType.STRING)
    private LeaveRequestStatus status = LeaveRequestStatus.PENDING; // Default status

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approved_by_user_id") // User who approved/rejected the leave (e.g., Manager or HR)
    private User approvedBy;

    private LocalDate decisionDate;

    @Lob
    private String approverComments;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    public enum LeaveType {
        ANNUAL_LEAVE,
        SICK_LEAVE,
        UNPAID_LEAVE,
        MATERNITY_LEAVE,
        PATERNITY_LEAVE,
        OTHER
    }

    // Enum déplacée vers LeaveRequestStatus.java
    // Conservée ici en tant que référence interne pour compatibilité avec le code existant
    @Deprecated
    public enum LeaveStatus {
        PENDING,
        APPROVED,
        REJECTED,
        CANCELLED
    }

    public LeaveRequest(User consultant, LeaveType leaveType, LocalDate startDate, LocalDate endDate, String reason) {
        this.consultant = consultant;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reason = reason;
    }
}
