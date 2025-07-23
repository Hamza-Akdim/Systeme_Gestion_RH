package com.talentwave.service.dto.langue;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akdim
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LangueDTO {
    private Long idL;

    @Size(max = 5)
    private String codeL;

    @Size(max = 100)
    private String titre;

    private String flag;
}
