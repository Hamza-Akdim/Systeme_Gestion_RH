package com.talentwave.service.dto.timesheet;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PublicHolidayDTO {
    private Long id;

    @NotBlank
    @Size(max = 255)
    private String name;

    @NotNull
    private LocalDate date;

    private String description;

    // Assuming this might be country-specific or region-specific if needed
    // private String countryCode;

    private Instant createdAt;

    private Instant updatedAt;
}

