package com.talentwave.service.impl.offre;

import com.talentwave.domain.offer.HardSkill;
import com.talentwave.domain.offer.JobOffer;
import com.talentwave.domain.offer.TaskMission;
import com.talentwave.repository.offre.JobOfferRepository;
import com.talentwave.service.dto.offre.HardSkillDTO;
import com.talentwave.service.dto.offre.TaskMissionDTO;
import com.talentwave.service.offre.JobOfferService;
import com.talentwave.service.dto.offre.JobOfferDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class JobOfferServiceImpl implements JobOfferService {

    @Autowired
    private JobOfferRepository jobOfferRepository;


    @Override
    public JobOfferDTO save(JobOfferDTO jobOfferDTO) {
        JobOffer entity = toEntity(jobOfferDTO);
        JobOffer saved = jobOfferRepository.save(entity);
        return toDto(saved);
    }

    @Override
    public Optional<JobOfferDTO> partialUpdate(JobOfferDTO jobOfferDTO) {
//        log.debug("Request to partially update JobOffer : {}", jobOfferDTO);
//
//        return jobOfferRepository
//            .findById(jobOfferDTO.getId())
//            .map(existingJobOffer -> {
//                if (jobOfferDTO.getTitle() != null) {
//                    existingJobOffer.setTitle(jobOfferDTO.getTitle());
//                }
//                if (jobOfferDTO.getDescription() != null) {
//                    existingJobOffer.setDescription(jobOfferDTO.getDescription());
//                }
//
//                if (jobOfferDTO.getStatus() != null) {
//                    existingJobOffer.setStatus(jobOfferDTO.getStatus());
//                }
//                if (jobOfferDTO.getClosingDate() != null) {
//                    existingJobOffer.setClosingDate(jobOfferDTO.getClosingDate());
//                }
//                if (jobOfferDTO.getJobProfileId() != null) {
//                    jobProfileRepository.findById(jobOfferDTO.getJobProfileId()).ifPresent(existingJobOffer::setJobProfile);
//                }
//                existingJobOffer.setUpdatedAt(Instant.now());
//                return existingJobOffer;
//            })
//            .map(jobOfferRepository::save)
//            .map(this::toDto);
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<JobOfferDTO> findAll(int page, int size) {
        Page<JobOffer> jobOfferPage = jobOfferRepository.findAll(PageRequest.of(page, size));
        return jobOfferPage.map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<JobOfferDTO> findOne(Long id) {
//        log.debug("Request to get JobOffer : {}", id);
//        return jobOfferRepository.findById(id).map(this::toDto);
        return null;
    }

    @Override
    public void delete(Long id) {
//        log.debug("Request to delete JobOffer : {}", id);
//        jobOfferRepository.deleteById(id);
    }

    private JobOffer toEntity(JobOfferDTO dto) {
        if (dto == null) return null;

        JobOffer jobOffer = new JobOffer();
        jobOffer.setTitle(dto.getTitle());
        jobOffer.setDescription(dto.getDescription());
        jobOffer.setContrat(dto.getContrat());
        jobOffer.setSecteur(dto.getSecteur());
        jobOffer.setStatus(dto.getStatus());
        jobOffer.setClosingDate(dto.getClosingDate());

        // Map TaskMissions
        if (dto.getTaskMissions() != null) {
            List<TaskMission> missions = dto.getTaskMissions().stream()
                    .map(tmDto -> {
                        TaskMission tm = new TaskMission();
                        tm.setTitle(tmDto.getTitle());
                        return tm;
                    }).toList();
            jobOffer.setTaskMissions(missions);
        }

        // Map HardSkills
        if (dto.getHardSkills() != null) {
            List<HardSkill> skills = dto.getHardSkills().stream()
                    .map(hsDto -> {
                        HardSkill hs = new HardSkill();
                        hs.setTitle(hsDto.getTitle());
                        hs.setLevel(hsDto.getLevel());
                        return hs;
                    }).toList();
            jobOffer.setHardSkills(skills);
        }

        return jobOffer;
    }

    private JobOfferDTO toDto(JobOffer entity) {
        if (entity == null) return null;

        JobOfferDTO dto = new JobOfferDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContrat(entity.getContrat());
        dto.setSecteur(entity.getSecteur());
        dto.setStatus(entity.getStatus());
        dto.setClosingDate(entity.getClosingDate());

        // Map TaskMissions
        if (entity.getTaskMissions() != null) {
            List<TaskMissionDTO> missions = entity.getTaskMissions().stream()
                    .map(tm -> {
                        TaskMissionDTO tmDto = new TaskMissionDTO();
                        tmDto.setId(tm.getId());
                        tmDto.setTitle(tm.getTitle());
                        return tmDto;
                    }).toList();
            dto.setTaskMissions(missions);
        }

        // Map HardSkills
        if (entity.getHardSkills() != null) {
            List<HardSkillDTO> skills = entity.getHardSkills().stream()
                    .map(hs -> {
                        HardSkillDTO hsDto = new HardSkillDTO();
                        hsDto.setId(hs.getId());
                        hsDto.setTitle(hs.getTitle());
                        hsDto.setLevel(hs.getLevel());
                        return hsDto;
                    }).toList();
            dto.setHardSkills(skills);
        }

        return dto;
    }
}


