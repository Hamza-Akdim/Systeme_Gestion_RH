package com.talentwave.domain.langue;

import com.talentwave.domain.profilCV.LangueConsultant;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author akdim
 */

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Langue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idL;

    @Column(length = 5, nullable = false, unique = true)
    @Size(max = 5)
    private String codeL;

    @Column(length = 100, nullable = false)
    @Size(max = 100)
    private String titre;

    @Column(length = 255)
    private String flag;

    @OneToMany(mappedBy = "langue", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<LangueConsultant> langueConsultants;
}
