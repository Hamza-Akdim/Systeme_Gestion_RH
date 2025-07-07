package com.talentwave.service.impl.timesheet;

import com.talentwave.domain.timesheet.PublicHoliday;
import com.talentwave.repository.timesheet.PublicHolidayRepository;
import com.talentwave.service.timesheet.PublicHolidayService;
import com.talentwave.service.dto.timesheet.PublicHolidayDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PublicHolidayServiceImpl implements PublicHolidayService {

    private final Logger log = LoggerFactory.getLogger(PublicHolidayServiceImpl.class);

    private final PublicHolidayRepository publicHolidayRepository;

    public PublicHolidayServiceImpl(PublicHolidayRepository publicHolidayRepository) {
        this.publicHolidayRepository = publicHolidayRepository;
    }

    @Override
    public PublicHolidayDTO save(PublicHolidayDTO publicHolidayDTO) {
        log.debug("Request to save PublicHoliday : {}", publicHolidayDTO);
        PublicHoliday publicHoliday = toEntity(publicHolidayDTO);
        if (publicHoliday.getId() == null) { // New entity
            publicHoliday.setCreatedAt(Instant.now());
        } else { // Existing entity
            publicHoliday.setUpdatedAt(Instant.now());
            // Créer une variable finale pour utilisation dans la lambda
            final PublicHoliday finalPublicHoliday = publicHoliday;
            publicHolidayRepository.findById(publicHoliday.getId()).ifPresent(existing -> finalPublicHoliday.setCreatedAt(existing.getCreatedAt()));
        }
        publicHoliday = publicHolidayRepository.save(publicHoliday);
        return toDto(publicHoliday);
    }

    @Override
    public Optional<PublicHolidayDTO> partialUpdate(PublicHolidayDTO publicHolidayDTO) {
        log.debug("Request to partially update PublicHoliday : {}", publicHolidayDTO);

        return publicHolidayRepository
            .findById(publicHolidayDTO.getId())
            .map(existingPublicHoliday -> {
                if (publicHolidayDTO.getName() != null) {
                    existingPublicHoliday.setName(publicHolidayDTO.getName());
                }
                if (publicHolidayDTO.getDate() != null) {
                    existingPublicHoliday.setDate(publicHolidayDTO.getDate());
                }
                if (publicHolidayDTO.getDescription() != null) {
                    existingPublicHoliday.setDescription(publicHolidayDTO.getDescription());
                }
                existingPublicHoliday.setUpdatedAt(Instant.now());
                return existingPublicHoliday;
            })
            .map(publicHolidayRepository::save)
            .map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PublicHolidayDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PublicHolidays");
        return publicHolidayRepository.findAll(pageable).map(this::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PublicHolidayDTO> findAllByDateBetween(LocalDate startDate, LocalDate endDate) {
        log.debug("Request to get all PublicHolidays between {} and {}", startDate, endDate);
        return publicHolidayRepository.findAllByDateBetweenOrderByDateAsc(startDate, endDate).stream()
            .map(this::toDto)
            .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PublicHolidayDTO> findOne(Long id) {
        log.debug("Request to get PublicHoliday : {}", id);
        return publicHolidayRepository.findById(id).map(this::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete PublicHoliday : {}", id);
        publicHolidayRepository.deleteById(id);
    }

    private PublicHolidayDTO toDto(PublicHoliday publicHoliday) {
        PublicHolidayDTO dto = new PublicHolidayDTO();
        dto.setId(publicHoliday.getId());
        dto.setName(publicHoliday.getName());
        dto.setDate(publicHoliday.getDate());
        dto.setDescription(publicHoliday.getDescription());
        dto.setCreatedAt(publicHoliday.getCreatedAt());
        dto.setUpdatedAt(publicHoliday.getUpdatedAt());
        return dto;
    }

    private PublicHoliday toEntity(PublicHolidayDTO dto) {
        PublicHoliday entity = new PublicHoliday();
        entity.setId(dto.getId());
        entity.setName(dto.getName());
        entity.setDate(dto.getDate());
        entity.setDescription(dto.getDescription());
        // createdAt and updatedAt are handled in save/update logic
        return entity;
    }
}

