package com.talentwave.service.impl.langue;

import com.talentwave.domain.langue.Langue;
import com.talentwave.repository.langue.LangueRespository;
import com.talentwave.service.dto.langue.LangueDTO;
import com.talentwave.service.langue.LangueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author akdim
 */

@Service
public class LangueServiceImpl implements LangueService {
    @Autowired
    LangueRespository langueRespository;

    @Override
    public LangueDTO addLangue(LangueDTO langueDTO) {
        Langue langue = new Langue();
        langue.setCodeL(langueDTO.getCodeL());
        langue.setTitre(langueDTO.getTitre());
        langue.setFlag(langueDTO.getFlag());

        langueRespository.save(langue);

        return toDTO(langue);
    }

    @Override
    public LangueDTO updateLangue(LangueDTO langueDTO) {
        return null;
    }

    @Override
    public void deleteLangue(Long idL) {

    }

    @Override
    public List<LangueDTO> getAllLangues() {
        List<LangueDTO> langues = langueRespository.findAll()
                .stream()
                .map(langue -> toDTO(langue))
                .toList();
        return langues;
    }


    private LangueDTO toDTO(Langue langue) {
        LangueDTO langueDTO = new LangueDTO(
                langue.getIdL(),
                langue.getCodeL(),
                langue.getTitre(),
                langue.getFlag()
        );
        return langueDTO;
    }
}
