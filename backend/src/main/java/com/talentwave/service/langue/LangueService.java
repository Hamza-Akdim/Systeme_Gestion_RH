package com.talentwave.service.langue;

import com.talentwave.service.dto.langue.LangueDTO;

import java.util.List;

public interface LangueService {
    LangueDTO addLangue(LangueDTO langueDTO);
    LangueDTO updateLangue(LangueDTO langueDTO);
    void deleteLangue(Long idL);
    List<LangueDTO> getAllLangues();

}
