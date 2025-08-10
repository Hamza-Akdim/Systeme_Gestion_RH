package com.talentwave.service.impl.profilCV;

import com.talentwave.domain.candidate.Candidate;
import com.talentwave.domain.langue.Langue;
import com.talentwave.domain.profilCV.LangueConsultant;
import com.talentwave.repository.candidate.CandidateRepository;
import com.talentwave.repository.langue.LangueRespository;
import com.talentwave.repository.profilCV.LangueConsultantRepository;
import com.talentwave.service.dto.profilCV.LangueConsultantDTO;
import com.talentwave.service.profilCV.LangueConsultantService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author akdim
 */

@Service
public class LangueConsultantServiceImpl implements LangueConsultantService {
    @Autowired
    LangueConsultantRepository langueConsultantRepository;

    @Autowired
    CandidateRepository candidateRepository;

    @Autowired
    LangueRespository langueRespository;

    @Override
    public LangueConsultantDTO addLangueConsultant(LangueConsultantDTO langueConsultantDTO) {
        LangueConsultant langueConsultant = toEntity(langueConsultantDTO);
        langueConsultantRepository.save(langueConsultant);
        return toDTO(langueConsultant);
    }

    @Override
    public LangueConsultantDTO updateLangueConsultant(LangueConsultantDTO langueConsultantDTO) {
//        Optional<LangueConsultant> optionalLangueConsultant = langueConsultantRepository.findById(langueConsultantDTO.getIdLC());
//
//        if (!optionalLangueConsultant.isPresent()) {
//            throw new EntityNotFoundException("LangueConsultant with ID " + langueConsultantDTO.getIdLC() + " not found.");
//        }
//
//        LangueConsultant langueConsultant = optionalLangueConsultant.get();
//
//        if (langueConsultantDTO.getLangue_id() != null) {
//            Langue langue = langueRespository.findById(langueConsultantDTO.getLangue_id())
//                    .orElseThrow(()-> new EntityNotFoundException("This language doesn't exist"));
//
//            langueConsultant.setLangue(langue);
//        }
//
//        if (langueConsultantDTO.getCandidate_id() != null) {
//            Candidate candidate = candidateRepository.findById(langueConsultantDTO.getCandidate_id())
//                    .orElseThrow(()-> new EntityNotFoundException("This candidate doesn't exist"));
//            langueConsultant.setCandidate(candidate);
//        }
//
//        if (langueConsultantDTO.getLevel() != null) {
//            langueConsultant.setLevel(langueConsultantDTO.getLevel());
//        }
//
//        return toDTO(langueConsultantRepository.save(langueConsultant));
        return null;
    }

    @Override
    public void deleteLangueConsultant(Long idLC) {
        Optional<LangueConsultant> optionalLangueConsultant = langueConsultantRepository.findById(idLC);

        if (!optionalLangueConsultant.isPresent()) {
            throw new EntityNotFoundException("LangueConsultant with ID " + idLC + " not found.");
        }

        langueConsultantRepository.deleteById(idLC);
    }

    @Override
    public LangueConsultantDTO getLangueConsultant(Long idLC) {
        LangueConsultant langueConsultant = langueConsultantRepository.findById(idLC)
                .orElseThrow(() -> new EntityNotFoundException("LangueConsultant with ID " + idLC + " not found."));

        return toDTO(langueConsultant);
    }

    private LangueConsultantDTO toDTO(LangueConsultant langueConsultant) {
        LangueConsultantDTO langueConsultantDTO = new LangueConsultantDTO();
        langueConsultantDTO.setIdLC(langueConsultant.getIdLC());
        langueConsultantDTO.setLevel(langueConsultant.getLevel());
        langueConsultantDTO.setLangue(langueConsultant.getLangue());
//        langueConsultantDTO.setCandidate_id(langueConsultant.getCandidate().getId());
        return langueConsultantDTO;
    }

    private LangueConsultant toEntity(LangueConsultantDTO dto) {
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
}
