package com.talentwave.service.dto.profilCV;

import com.talentwave.domain.enumeration.Level;
import com.talentwave.domain.langue.Langue;
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
    private Langue langue;
//    private Long candidate_id;
}
