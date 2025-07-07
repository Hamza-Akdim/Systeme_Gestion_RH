package com.talentwave.domain.sourcing;

import com.talentwave.domain.User; // Assuming HR user who performed sourcing
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;

@Entity
@Table(name = "sourcing_stat")
@Getter
@Setter
@NoArgsConstructor
public class SourcingStat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_offer_id")
    private JobOffer jobOffer;

    @NotNull
    private String channel; // e.g., LinkedIn, Company Website, Referral

    @NotNull
    @Min(0)
    private Integer candidatesSourced = 0;

    @NotNull
    @Min(0)
    private Integer applicationsReceived = 0;

    @NotNull
    private LocalDate statDate; // Date for which the stats are recorded

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hr_user_id") // User who is responsible for this sourcing stat
    private User hrUser;

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    public SourcingStat(JobOffer jobOffer, String channel, Integer candidatesSourced, Integer applicationsReceived, LocalDate statDate, User hrUser) {
        this.jobOffer = jobOffer;
        this.channel = channel;
        this.candidatesSourced = candidatesSourced;
        this.applicationsReceived = applicationsReceived;
        this.statDate = statDate;
        this.hrUser = hrUser;
    }
}

