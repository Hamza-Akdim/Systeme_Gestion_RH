package com.talentwave.service.dto.profilCV;

import com.talentwave.domain.profilCV.ProfileCV;
import com.talentwave.domain.profilCV.Skill;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akdim
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO {
    private Long id;

    @NotBlank
    @Column(length = 255)
    private String title;

    @NotNull
    private Skill.Category category;

    public enum Category {
        TECHNICAL_SKILLS,
        SOFT_SKILLS,
        MANAGEMENT_SKILLS
    }
}
