package com.talentwave.service.dto.profilCV;

import com.talentwave.domain.enumeration.Level;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akdim
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LangueConsultantDTO {
    private Long idLC;
    private Level level;
    private Long langue_id;
    private Long candidate_id;
}
