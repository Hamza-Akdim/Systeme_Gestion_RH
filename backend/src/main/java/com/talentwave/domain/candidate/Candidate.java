package com.talentwave.domain.candidate;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.talentwave.domain.profilCV.LangueConsultant;
import com.talentwave.domain.profilCV.ProfileCV;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "candidate")  //Consultant
@Data
@NoArgsConstructor
public class Candidate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    private String firstName;

    @NotBlank
    @Size(max = 100)
    private String lastName;

    @NotBlank
    @Email
    @Size(max = 100)
    @Column(unique = true)
    private String email;

    @Size(max = 20)
    private String phoneNumber;

    @Lob
    private String resumeUrl; // URL to the resume file (e.g., S3, local storage)

    @Lob
    private String coverLetterText;

    private String linkedInProfileUrl;

    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    //This annotation doesn't allow this field to be recursive in the JSON response
    @JsonBackReference //back side to ignore in JSON. Means this field is not appeared in JSON Response
    private List<ProfileCV> profileCVs;

//    @OneToMany(mappedBy = "candidate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<LangueConsultant> langueConsultants;

    // Consider adding fields like currentSalary, expectedSalary, availabilityDate, etc.
    // Also, a relationship to Skills entity might be useful.

    @Column(name = "created_at", updatable = false)
    private Instant createdAt = Instant.now();

    @Column(name = "updated_at")
    private Instant updatedAt;

    public Candidate(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

}

