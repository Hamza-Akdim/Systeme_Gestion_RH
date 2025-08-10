package com.talentwave.domain.profilCV;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author akdim
 */

@Entity
@Data
@AllArgsConstructor @NoArgsConstructor
public class MissionProject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titleMission;

    private Date startDate;

    private Date endDate;

    private String description;

    private String techniqueTools;

}
