package com.talentwave.domain.profilCV;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * @author akdim
 */

@Entity
@Data
public class Langue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idL;

    @Column(length = 5, nullable = false, unique = true)
    @NotBlank
    @Size(max = 5)
    private String codeL;

    @Column(length = 100, nullable = false)
    @NotBlank
    @Size(max = 100)
    private String titre;

    @Column(length = 255)
    private String flag;

}
