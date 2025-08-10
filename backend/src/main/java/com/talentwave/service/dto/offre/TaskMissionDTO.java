package com.talentwave.service.dto.offre;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akdim
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskMissionDTO {
    private Long id;

    @NotNull
    private String title;
}
