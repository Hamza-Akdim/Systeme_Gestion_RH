package com.talentwave.service.profilCV;

import com.talentwave.service.dto.profilCV.LangueConsultantDTO;

public interface LangueConsultantService {
    LangueConsultantDTO addLangueConsultant(LangueConsultantDTO langueConsultantDTO);
    LangueConsultantDTO updateLangueConsultant(LangueConsultantDTO langueConsultantDTO);
    void deleteLangueConsultant(Long idLC);
    LangueConsultantDTO getLangueConsultant(Long idLC);
}
