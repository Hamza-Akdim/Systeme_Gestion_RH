package com.talentwave.service.dto.profilCV;

import com.talentwave.domain.candidate.Candidate;
import com.talentwave.domain.profilCV.Diplome;
import com.talentwave.domain.profilCV.LangueConsultant;
import com.talentwave.domain.profilCV.MissionProject;
import com.talentwave.domain.profilCV.Skill;

import java.util.List;

/**
 * @author akdim
 */

public class Mapper {
    // ---------------------CANDIDATE MAPPING----------------------------------
    public static CandidateDTO toDto(Candidate candidate) {
        CandidateDTO dto = new CandidateDTO();
        dto.setId(candidate.getId());
        dto.setFirstName(candidate.getFirstName());
        dto.setLastName(candidate.getLastName());
        dto.setEmail(candidate.getEmail());
        dto.setPhoneNumber(candidate.getPhoneNumber());
        dto.setResumeUrl(candidate.getResumeUrl());
        dto.setCoverLetterText(candidate.getCoverLetterText());
        dto.setLinkedInProfileUrl(candidate.getLinkedInProfileUrl());
        dto.setCreatedAt(candidate.getCreatedAt());
        dto.setUpdatedAt(candidate.getUpdatedAt());
        return dto;
    }

    public static Candidate toEntity(CandidateDTO dto) {
        // Utiliser le constructeur sans argument pour éviter les erreurs de compilation
        Candidate entity = new Candidate();
        entity.setId(dto.getId());
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setResumeUrl(dto.getResumeUrl());
        entity.setCoverLetterText(dto.getCoverLetterText());
        entity.setLinkedInProfileUrl(dto.getLinkedInProfileUrl());
        // createdAt and updatedAt are handled in save/update logic
        return entity;
    }

    // ------------------SKILL MAPPING ---------------------------------------

    public static SkillDTO toDTO(Skill entity) {
        if (entity == null) {
            return null;
        }
        SkillDTO dto = new SkillDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setCategory(entity.getCategory());
        return dto;
    }

    public static Skill toEntity(SkillDTO dto) {
        if (dto == null) {
            return null;
        }
        Skill entity = new Skill();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setCategory(dto.getCategory());
        // profileCV is not set here — handled separately to avoid recursion
        return entity;
    }

    // ---------------------DIPLOMA MAPPING----------------------------------

    public static DiplomeDTO toDTO(Diplome entity) {
        if (entity == null) {
            return null;
        }
        DiplomeDTO dto = new DiplomeDTO();
        dto.setId(entity.getId());
        dto.setTitre(entity.getTitre());
        dto.setNiveau(entity.getNiveau());
        dto.setOrganismeDelivred(entity.getOrganismeDelivred());
        dto.setDateDebut(entity.getDateDebut());
        dto.setDateFin(entity.getDateFin());
        dto.setCategorieDiplome(entity.getCategorieDiplome());
        dto.setPathFile(entity.getPathFile());
        dto.setQrCode(entity.getQrCode());
        return dto;
    }

    public static Diplome toEntity(DiplomeDTO dto) {
        if (dto == null) {
            return null;
        }
        Diplome entity = new Diplome();
        entity.setId(dto.getId());
        entity.setTitre(dto.getTitre());
        entity.setNiveau(dto.getNiveau());
        entity.setOrganismeDelivred(dto.getOrganismeDelivred());
        entity.setDateDebut(dto.getDateDebut());
        entity.setDateFin(dto.getDateFin());
        entity.setCategorieDiplome(dto.getCategorieDiplome());
        entity.setPathFile(dto.getPathFile());
        entity.setQrCode(dto.getQrCode());
        return entity;
    }

    // ---------------------MissionProject MAPPING----------------------------

    public static MissionProjectDTO toDTO(MissionProject entity) {
        if (entity == null) {
            return null;
        }
        MissionProjectDTO dto = new MissionProjectDTO();
        dto.setId(entity.getId());
        dto.setTitleMission(entity.getTitleMission());
        dto.setStartDate(entity.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setDescription(entity.getDescription());
        dto.setTechniqueTools(entity.getTechniqueTools());
        return dto;
    }

    public static MissionProject toEntity(MissionProjectDTO dto) {
        if (dto == null) {
            return null;
        }
        MissionProject entity = new MissionProject();
        entity.setId(dto.getId());
        entity.setTitleMission(dto.getTitleMission());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setDescription(dto.getDescription());
        entity.setTechniqueTools(dto.getTechniqueTools());
        return entity;
    }

    //------------------------Langues Consultants Mapping-----------------------

    public static LangueConsultantDTO toDTO(LangueConsultant langueConsultant) {
        LangueConsultantDTO langueConsultantDTO = new LangueConsultantDTO();
        langueConsultantDTO.setIdLC(langueConsultant.getIdLC());
        langueConsultantDTO.setLevel(langueConsultant.getLevel());
        langueConsultantDTO.setLangue(langueConsultant.getLangue());
//        langueConsultantDTO.setCandidate_id(langueConsultant.getCandidate().getId());
        return langueConsultantDTO;
    }

    public static LangueConsultant toEntity(LangueConsultantDTO dto) {
//        Candidate candidate = candidateRepository.findById(dto.getCandidate_id())
//                .orElseThrow(()-> new EntityNotFoundException("This candidate doesn't exist"));
//        Langue langue = langueRespository.findById(dto.getLangue_id())
//                .orElseThrow(()-> new EntityNotFoundException("This language doesn't exist"));

        LangueConsultant lc = new LangueConsultant();
        lc.setLangue(dto.getLangue());
        lc.setLevel(dto.getLevel());
//        lc.setCandidate(candidate);
        return lc;
    }

    //-------------------------LIST DTO---------------------------------------

    public static List<SkillDTO> toDTOListSkill(List<Skill> entities) {
        return entities == null ? null : entities.stream()
                .map(Mapper::toDTO)
                .toList();
    }

    public static List<Skill> toEntityListSkill(List<SkillDTO> dtos) {
        return dtos == null ? null : dtos.stream()
                .map(Mapper::toEntity)
                .toList();
    }

    public static List<DiplomeDTO> toDTOListDiplomas(List<Diplome> entities) {
        return entities == null ? null : entities.stream()
                .map(Mapper::toDTO)
                .toList();
    }

    public static List<Diplome> toEntityListDiplomas(List<DiplomeDTO> dtos) {
        return dtos == null ? null : dtos.stream()
                .map(Mapper::toEntity)
                .toList();
    }

    public static List<MissionProjectDTO> toDTOListMissionPorject(List<MissionProject> entities) {
        return entities == null ? null : entities.stream()
                .map(Mapper::toDTO)
                .toList();
    }

    public static List<MissionProject> toEntityListMissionPorject(List<MissionProjectDTO> dtos) {
        return dtos == null ? null : dtos.stream()
                .map(Mapper::toEntity)
                .toList();
    }

    public static List<LangueConsultantDTO> toDTOListLangueConsultant(List<LangueConsultant> entities) {
        return entities == null ? null : entities.stream()
                .map(Mapper::toDTO)
                .toList();
    }

    public static List<LangueConsultant> toEntityListLangueConsultant(List<LangueConsultantDTO> dtos) {
        return dtos == null ? null : dtos.stream()
                .map(Mapper::toEntity)
                .toList();
    }


}
