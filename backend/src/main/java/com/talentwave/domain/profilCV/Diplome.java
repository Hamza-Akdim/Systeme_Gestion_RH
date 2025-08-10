package com.talentwave.domain.profilCV;

import com.talentwave.domain.enumeration.CategorieDiplome;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author akdim
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Diplome {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    private String niveau;
    private String organismeDelivred;
    private Date dateDebut;
    private Date dateFin;

    @Enumerated(EnumType.STRING)
    private CategorieDiplome categorieDiplome;

    private String pathFile;
    private String qrCode;
}
