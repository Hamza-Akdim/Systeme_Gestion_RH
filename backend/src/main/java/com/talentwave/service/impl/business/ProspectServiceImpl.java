package com.talentwave.service.impl.business;

import com.talentwave.domain.User;
import com.talentwave.domain.business.Prospect;
import com.talentwave.domain.business.Prospect.ProspectStatus;
import com.talentwave.repository.UserRepository;
import com.talentwave.repository.business.ProspectRepository;
import com.talentwave.service.business.ProspectService;
import com.talentwave.service.dto.business.ProspectDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProspectServiceImpl implements ProspectService {

    private final Logger log = LoggerFactory.getLogger(ProspectServiceImpl.class);

    private final ProspectRepository prospectRepository;
    private final UserRepository userRepository;

    public ProspectServiceImpl(ProspectRepository prospectRepository, UserRepository userRepository) {
        this.prospectRepository = prospectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProspectDTO save(ProspectDTO prospectDTO) {
        log.debug("Request to save Prospect : {}", prospectDTO);
        Prospect prospect = toEntity(prospectDTO);
        if (prospect.getId() == null) { // New entity
            prospect.setCreatedAt(Instant.now());
        } else { // Existing entity
            prospect.setUpdatedAt(Instant.now());
            // Créer une variable finale pour utilisation dans la lambda
            final Prospect finalProspect = prospect;
            prospectRepository.findById(prospect.getId()).ifPresent(existing -> finalProspect.setCreatedAt(existing.getCreatedAt()));
        }
        prospect = prospectRepository.save(prospect);
        return toDto(prospect);
    }

    @Override
    public Optional<ProspectDTO> partialUpdate(ProspectDTO prospectDTO) {
        log.debug("Request to partially update Prospect : {}", prospectDTO);

        return prospectRepository
            .findById(prospectDTO.getId())
            .map(existingProspect -> {
                if (prospectDTO.getCompanyName() != null) {
                    existingProspect.setCompanyName(prospectDTO.getCompanyName());
                }
                if (prospectDTO.getContactName() != null) {
                    existingProspect.setContactName(prospectDTO.getContactName());
                }
                if (prospectDTO.getEmail() != null) {
                    existingProspect.setEmail(prospectDTO.getEmail());
                }
                if (prospectDTO.getPhoneNumber() != null) {
                    existingProspect.setPhoneNumber(prospectDTO.getPhoneNumber());
                }
                if (prospectDTO.getAddress() != null) {
                    existingProspect.setAddress(prospectDTO.getAddress());
                }
                if (prospectDTO.getStatus() != null) {
                    // Conversion du ProspectStatus en String pour la compatibilité
                    existingProspect.setStatus(prospectDTO.getStatus().toString());
                }
                if (prospectDTO.getFirstContactDate() != null) {
                    existingProspect.setFirstContactDate(prospectDTO.getFirstContactDate());
                }
                if (prospectDTO.getLastContactDate() != null) {
                    existingProspect.setLastContactDate(prospectDTO.getLastContactDate());
                }
                if (prospectDTO.getNotes() != null) {
                    existingProspect.setNotes(prospectDTO.getNotes());
                }
                if (prospectDTO.getResponsibleUserId() != null) {
                    userRepository.findById(prospectDTO.getResponsibleUserId()).ifPresent(existingProspect::setResponsibleUser);
                }
                existingProspect.setUpdatedAt(Instant.now());
                return existingProspect;
            })
            .map(prospectRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProspectDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Prospects");
        return prospectRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProspectDTO> findAllByResponsibleUserId(Long responsibleUserId) {
        log.debug("Request to get all Prospects for Responsible User ID: {}", responsibleUserId);
        return prospectRepository.findByResponsibleUserId(responsibleUserId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ProspectDTO> findOne(Long id) {
        log.debug("Request to get Prospect : {}", id);
        return prospectRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prospect : {}", id);
        prospectRepository.deleteById(id);
    }

    private ProspectDTO toDto(Prospect prospect) {
        ProspectDTO dto = new ProspectDTO();
        dto.setId(prospect.getId());
        dto.setCompanyName(prospect.getCompanyName());
        dto.setContactName(prospect.getContactName());
        dto.setEmail(prospect.getEmail());
        dto.setPhoneNumber(prospect.getPhoneNumber());
        dto.setAddress(prospect.getAddress());
        dto.setStatus(prospect.getStatus());
        dto.setFirstContactDate(prospect.getFirstContactDate());
        dto.setLastContactDate(prospect.getLastContactDate());
        dto.setNotes(prospect.getNotes());
        if (prospect.getResponsibleUser() != null) {
            dto.setResponsibleUserId(prospect.getResponsibleUser().getId());
            dto.setResponsibleUserName(prospect.getResponsibleUser().getUsername()); // Or full name
        }
        dto.setCreatedAt(prospect.getCreatedAt());
        dto.setUpdatedAt(prospect.getUpdatedAt());
        return dto;
    }

    private Prospect toEntity(ProspectDTO dto) {
        // Utiliser le constructeur sans argument pour éviter les erreurs de compilation
        Prospect entity = new Prospect();
        entity.setId(dto.getId());
        entity.setCompanyName(dto.getCompanyName());
        entity.setIndustry(dto.getIndustry());
        entity.setContactName(dto.getContactName());
        entity.setEmail(dto.getEmail());
        entity.setPhoneNumber(dto.getPhoneNumber());
        entity.setAddress(dto.getAddress());
        // Utiliser directement la valeur String comme status
        if (dto.getStatus() != null) {
            entity.setStatus(dto.getStatus());
        }
        entity.setFirstContactDate(dto.getFirstContactDate());
        entity.setLastContactDate(dto.getLastContactDate());
        entity.setNotes(dto.getNotes());
        if (dto.getResponsibleUserId() != null) {
            com.talentwave.domain.User responsibleUser = userRepository.findById(dto.getResponsibleUserId())
                .orElseThrow(() -> new RuntimeException("User (Responsible) not found with id: " + dto.getResponsibleUserId()));
            entity.setResponsibleUser(responsibleUser);
        }
        // createdAt and updatedAt are handled in save/update logic
        return entity;
    }
}

