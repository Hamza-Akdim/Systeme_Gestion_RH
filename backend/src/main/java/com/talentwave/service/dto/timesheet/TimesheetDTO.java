package com.talentwave.service.dto.timesheet;

import com.talentwave.domain.timesheet.Timesheet.TimesheetStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class TimesheetDTO {
    private Long id;

    @NotNull
    private Long consultantId;
    private String consultantName; // Optional: for display

    @NotNull
    private Long missionId; // Or projectId, depending on your model
    private String missionTitle; // Optional: for display

    @NotNull
    private LocalDate workDate;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false) // Assuming hours worked must be positive
    private BigDecimal hoursWorked;

    private String description;

    @NotNull
    private TimesheetStatus status;

    private Long approvedById; // User ID of the approver
    private String approvedByName; // Optional: for display

    private Instant createdAt;

    private Instant updatedAt;
}

