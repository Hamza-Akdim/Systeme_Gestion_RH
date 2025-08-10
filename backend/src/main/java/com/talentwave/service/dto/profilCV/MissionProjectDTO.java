package com.talentwave.service.dto.profilCV;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author akdim
 */

@Data
@NoArgsConstructor @AllArgsConstructor
public class MissionProjectDTO {
    private Long id;

    private String titleMission;

    private Date startDate;

    private Date endDate;

    private String description;

    private String techniqueTools;
}
