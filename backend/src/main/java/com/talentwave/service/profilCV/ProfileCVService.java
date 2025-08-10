package com.talentwave.service.profilCV;

import com.talentwave.service.dto.profilCV.ProfileCVDTO;

import java.io.File;
import java.util.List;

public interface ProfileCVService {
    ProfileCVDTO addProfileCV(ProfileCVDTO profileCVDTO);
    ProfileCVDTO updateProfileCV(ProfileCVDTO profileCVDTO);
    void deleteProfileCV(Long id);
    List<ProfileCVDTO> getAllProfileCV();
    void ImportProfileCV(File CV);
    File ExportProfileCV(Long id);
}
