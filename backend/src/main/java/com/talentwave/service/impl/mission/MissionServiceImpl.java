package com.talentwave.service.impl.mission;

import com.talentwave.domain.User;
import com.talentwave.domain.mission.Mission;
// Assuming a Client entity exists, if not, this needs to be adapted or clientId remains a Long.
// import com.talentwave.domain.client.Client; 
// import com.talentwave.repository.client.ClientRepository;
import com.talentwave.repository.mission.MissionRepository;
import com.talentwave.service.mission.MissionService;
import com.talentwave.service.dto.mission.MissionDTO;
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
public class MissionServiceImpl implements MissionService {

    private final Logger log = LoggerFactory.getLogger(MissionServiceImpl.class);

    private final MissionRepository missionRepository;
    // private final ClientRepository clientRepository; // Uncomment if Client entity is used

    public MissionServiceImpl(MissionRepository missionRepository /*, ClientRepository clientRepository */) {
        this.missionRepository = missionRepository;
        // this.clientRepository = clientRepository; // Uncomment if Client entity is used
    }

    @Override
    public MissionDTO save(MissionDTO missionDTO) {
        log.debug("Request to save Mission : {}", missionDTO);
        Mission mission = toEntity(missionDTO);
        if (mission.getId() == null) { // New entity
            mission.setCreatedAt(Instant.now());
        } else { // Existing entity
            mission.setUpdatedAt(Instant.now());
            // Créer une variable finale pour utilisation dans la lambda
            final Mission finalMission = mission;
            missionRepository.findById(mission.getId()).ifPresent(existing -> finalMission.setCreatedAt(existing.getCreatedAt()));
        }
        mission = missionRepository.save(mission);
        return toDto(mission);
    }

    @Override
    public Optional<MissionDTO> partialUpdate(MissionDTO missionDTO) {
        log.debug("Request to partially update Mission : {}", missionDTO);

        return missionRepository
            .findById(missionDTO.getId())
            .map(existingMission -> {
                if (missionDTO.getTitle() != null) {
                    existingMission.setTitle(missionDTO.getTitle());
                }
                if (missionDTO.getDescription() != null) {
                    existingMission.setDescription(missionDTO.getDescription());
                }
                if (missionDTO.getStartDate() != null) {
                    existingMission.setStartDate(missionDTO.getStartDate());
                }
                if (missionDTO.getEndDate() != null) {
                    existingMission.setEndDate(missionDTO.getEndDate());
                }
                if (missionDTO.getStatus() != null) {
                    existingMission.setStatus(missionDTO.getStatus());
                }
                if (missionDTO.getClientId() != null) {
                    // Assuming Client entity is not yet implemented, storing as Long
                    // If Client entity is implemented, fetch and set it:
                    // clientRepository.findById(missionDTO.getClientId()).ifPresent(existingMission::setClient);
                    existingMission.setClientId(missionDTO.getClientId()); 
                }
                existingMission.setUpdatedAt(Instant.now());
                return existingMission;
            })
            .map(missionRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<MissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Missions");
        return missionRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MissionDTO> findAllByClientId(Long clientId) {
        log.debug("Request to get all Missions for Client ID: {}", clientId);
        // Assuming direct storage of clientId in Mission entity for now
        return missionRepository.findByClientId(clientId).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<MissionDTO> findOne(Long id) {
        log.debug("Request to get Mission : {}", id);
        return missionRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Mission : {}", id);
        missionRepository.deleteById(id);
    }

    private MissionDTO toDto(Mission mission) {
        MissionDTO dto = new MissionDTO();
        dto.setId(mission.getId());
        dto.setTitle(mission.getTitle());
        dto.setDescription(mission.getDescription());
        dto.setStartDate(mission.getStartDate());
        dto.setEndDate(mission.getEndDate());
        dto.setStatus(mission.getStatus());
        // Assuming direct storage of clientId in Mission entity for now
        dto.setClientId(mission.getClientId()); 
        // if (mission.getClient() != null) { // Uncomment if Client entity is used
        //     dto.setClientId(mission.getClient().getId());
        //     dto.setClientName(mission.getClient().getName()); // Assuming Client has a getName() method
        // }
        dto.setCreatedAt(mission.getCreatedAt());
        dto.setUpdatedAt(mission.getUpdatedAt());
        return dto;
    }

    private Mission toEntity(MissionDTO dto) {
        // Utiliser le constructeur sans argument pour éviter les erreurs de compilation
        Mission entity = new Mission();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setDescription(dto.getDescription());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setStatus(dto.getStatus());
        // Assuming direct storage of clientId in Mission entity for now
        entity.setClientId(dto.getClientId());
        // if (dto.getClientId() != null) { // Uncomment if Client entity is used
        //     Client client = clientRepository.findById(dto.getClientId())
        //         .orElseThrow(() -> new RuntimeException("Client not found with id: " + dto.getClientId()));
        //     entity.setClient(client);
        // }
        // createdAt and updatedAt are handled in save/update logic
        return entity;
    }
}

