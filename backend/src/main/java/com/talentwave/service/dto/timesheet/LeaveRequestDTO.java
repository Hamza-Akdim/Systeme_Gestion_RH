package com.talentwave.service.dto.timesheet;

import com.talentwave.domain.timesheet.LeaveRequestStatus;
import com.talentwave.domain.timesheet.LeaveRequest.LeaveType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class LeaveRequestDTO {
    private Long id;

    @NotNull
    private Long consultantId;
    private String consultantName; // Optional: for display

    @NotNull
    private LeaveType leaveType;

    @NotNull
    @FutureOrPresent
    private LocalDate startDate;

    @NotNull
    @FutureOrPresent // Assuming end date should also be in the future or present relative to creation
    private LocalDate endDate;

    @NotBlank
    private String reason;

    @NotNull
    private LeaveRequestStatus status;

    private Long approvedById; // User ID of the approver
    private String approvedByName; // Optional: for display

    private String approverComments;

    private Instant createdAt;

    private Instant updatedAt;
}
