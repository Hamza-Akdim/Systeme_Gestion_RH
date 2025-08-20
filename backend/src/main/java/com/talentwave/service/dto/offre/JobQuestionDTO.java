package com.talentwave.service.dto.offre;

import com.talentwave.domain.offer.JobQuestion;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author akdim
 */

@Data
@NoArgsConstructor @AllArgsConstructor
public class JobQuestionDTO {
    private Long id;

    @NotNull
    private String question;

    @NotNull
    private JobQuestion.ResponseType responseType;

    private List<String> selectOptions;

    @NotNull
    private String important;   //Y/N

}
