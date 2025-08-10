package com.talentwave.service.dto.profilCV;

import com.talentwave.domain.candidate.Candidate;
import com.talentwave.domain.profilCV.Diplome;
import com.talentwave.domain.profilCV.LangueConsultant;
import com.talentwave.domain.profilCV.MissionProject;
import com.talentwave.domain.profilCV.Skill;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author akdim
 */

@Data
@NoArgsConstructor @AllArgsConstructor
public class ProfileCVDTO {
    private Long id;

    private String title;

    private List<DiplomeDTO> diplomasDTO;

    private List<SkillDTO> skillDTOs;

    private List<LangueConsultantDTO> langueConsultants;

    private CandidateDTO candidateDTO;

    private List<MissionProjectDTO> missionProjectDTOs;
}
