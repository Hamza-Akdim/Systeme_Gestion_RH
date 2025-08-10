package com.talentwave.service.dto.profilCV;

import com.talentwave.domain.enumeration.CategorieDiplome;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author akdim
 */
@Data
@NoArgsConstructor @AllArgsConstructor
public class DiplomeDTO {
    private Long id;
    private String titre;
    private String niveau;
    private String organismeDelivred;
    private Date dateDebut;
    private Date dateFin;
    private CategorieDiplome categorieDiplome;
    private String pathFile;
    private String qrCode;
}
