package com.talentwave.service.impl.profilCV;

import com.talentwave.domain.profilCV.ProfileCV;
import com.talentwave.repository.profilCV.ProfileCVRepository;
import com.talentwave.service.dto.profilCV.ProfileCVDTO;
import com.talentwave.service.profilCV.ProfileCVService;
import com.talentwave.service.dto.profilCV.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author akdim
 */

@Service
public class ProfileCVServiceImpl implements ProfileCVService {
    @Autowired
    ProfileCVRepository profileCVRepository;

    @Override
    public ProfileCVDTO addProfileCV(ProfileCVDTO profileCVDTO) {
        ProfileCV profileCV = mapToEntity(profileCVDTO);
        ProfileCV saved = profileCVRepository.save(profileCV);
        return mapToDTO(saved);
    }

    @Override
    public ProfileCVDTO updateProfileCV(ProfileCVDTO profileCVDTO) {
//        ProfileCV existing = profileCVRepository.findById(profileCVDTO.getId())
//                .orElseThrow(() -> new RuntimeException("ProfileCV not found"));
//
//        if (profileCVDTO.getTitle() != null) {
//            existing.setTitle(profileCVDTO.getTitle());
//        }
//
//        if (profileCVDTO.getCandidateDTO() != null) {
//            existing.setCandidate(profileCVDTO.getCandidate());
//        }
//
//        if (profileCVDTO.getDiplomas() != null) {
//            existing.setDiplomas(profileCVDTO.getDiplomas());
//        }
//
//        if (profileCVDTO.getSkills() != null) {
//            existing.setSkills(profileCVDTO.getSkills());
//        }
//
//        if (profileCVDTO.getLangueConsultants() != null) {
//            existing.setLangueConsultants(profileCVDTO.getLangueConsultants());
//        }
//
//        if (profileCVDTO.getMissionProjects() != null) {
//            existing.setMissionProjects(profileCVDTO.getMissionProjects());
//        }
//
//        ProfileCV saved = profileCVRepository.save(existing);
//        return mapToDTO(saved);
        return null;
    }


    @Override
    public void deleteProfileCV(Long id) {
        profileCVRepository.deleteById(id);
    }

    @Override
    public List<ProfileCVDTO> getAllProfileCV() {
        return profileCVRepository.findAll().stream()
                .map(profileCV -> mapToDTO(profileCV)
                ).toList();

    }


    @Override
    public void ImportProfileCV(File CV) {

    }

    @Override
    public File ExportProfileCV(Long id) {
        return null;
    }

    private ProfileCV mapToEntity(ProfileCVDTO dto) {
        ProfileCV entity = new ProfileCV();
        entity.setTitle(dto.getTitle());
        entity.setCandidate(Mapper.toEntity(dto.getCandidateDTO()));
        entity.setDiplomas(Mapper.toEntityListDiplomas(dto.getDiplomasDTO()));
        entity.setSkills(Mapper.toEntityListSkill(dto.getSkillDTOs()));
        entity.setLangueConsultants(Mapper.toEntityListLangueConsultant(dto.getLangueConsultants()));
        entity.setMissionProjects(Mapper.toEntityListMissionPorject(dto.getMissionProjectDTOs()));
        return entity;
    }

    private ProfileCVDTO mapToDTO(ProfileCV entity) {
        ProfileCVDTO dto = new ProfileCVDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setCandidateDTO(Mapper.toDto(entity.getCandidate()));
        dto.setDiplomasDTO(Mapper.toDTOListDiplomas(entity.getDiplomas()));
        dto.setSkillDTOs(Mapper.toDTOListSkill(entity.getSkills()));
        dto.setLangueConsultants(Mapper.toDTOListLangueConsultant(entity.getLangueConsultants()));
        dto.setMissionProjectDTOs(Mapper.toDTOListMissionPorject(entity.getMissionProjects()));
        return dto;
    }
}
