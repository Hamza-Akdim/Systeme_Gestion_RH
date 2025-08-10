package com.talentwave.domain.profilCV;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.talentwave.domain.candidate.Candidate;
import com.talentwave.domain.offer.HardSkill;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author akdim
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileCV {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Diplome> diplomas;

    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(
            name = "profileCV_skill",
            joinColumns = @JoinColumn(name = "profileCV_id"),
            inverseJoinColumns = @JoinColumn(name = "skill_id")
    )
    private List<Skill> skills;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LangueConsultant> langueConsultants;

    @ManyToOne
    //This annotation doesn't allow this field to be recursive in the JSON response
    @JsonManagedReference // forward side to serialize. Means this field will be appeared in JSON Response, but for the related field in the Candidate Class will not be appeared for not having recursivity
    private Candidate candidate;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(
            name = "profileCV_id",
            nullable = false
    )
    private List<MissionProject> missionProjects;
}

