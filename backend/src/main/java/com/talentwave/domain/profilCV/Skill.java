package com.talentwave.domain.profilCV;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;


/**
 * @author akdim
 */

@Entity
@Table(name = "skills")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Column(length = 255)
    private String title;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Category category;

    @ManyToMany(mappedBy = "skills")
    private List<ProfileCV> profileCV;

    public enum Category {
        TECHNICAL_SKILLS,
        SOFT_SKILLS,
        MANAGEMENT_SKILLS
    }
}
