package com.talentwave.service.dto.sourcing;

import jakarta.validation.constraints.Min;
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
public class SourcingStatDTO {
    private Long id;

    @NotNull
    private Long jobOfferId;
    private String jobOfferTitle; // Optional: for display

    @NotBlank
    private String channel;

    @NotNull
    @Min(0)
    private Integer candidatesSourced;

    @NotNull
    @Min(0)
    private Integer applicationsReceived;

    @NotNull
    private LocalDate statDate;

    private Long hrUserId; // ID of the HR user responsible
    private String hrUserName; // Optional: for display

    private Instant createdAt;
}

