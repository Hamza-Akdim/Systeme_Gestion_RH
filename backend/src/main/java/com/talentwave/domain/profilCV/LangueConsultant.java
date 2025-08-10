package com.talentwave.domain.profilCV;

import com.talentwave.domain.candidate.Candidate;
import com.talentwave.domain.enumeration.Level;
import com.talentwave.domain.langue.Langue;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akdim
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LangueConsultant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLC;

    @Enumerated(EnumType.STRING)
    private Level level;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "langue_id", referencedColumnName = "idL", nullable = false) // referencedColumnName : it makes the mapping explicit and readable. doesn't have any functionality because "idL" is already the primary key which this foreign key is pointing to
    private Langue langue;

//    @ManyToOne()
//    @JoinColumn(name = "consultant_id", referencedColumnName = "id", nullable = false)
//    private Candidate candidate;
}
