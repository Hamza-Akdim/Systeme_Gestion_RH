package com.talentwave.domain.offer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
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
@NoArgsConstructor @AllArgsConstructor
public class JobQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String question;

    @NotNull
    private ResponseType responseType;

    @ElementCollection  //tells JPA this is a collection of basic types
    @CollectionTable(name = "select_options", joinColumns = @JoinColumn(name = "id_job_question"))
    @Column(name = "option")
    private List<String> selectOptions;

    @NotNull
    private String important;   //Y/N


    public enum ResponseType {
        TEXT,
        SELECT
    }

    @PrePersist
    @PreUpdate
    private void validateSelectOptions() {
        if (responseType == ResponseType.SELECT &&
                (selectOptions == null || selectOptions.isEmpty())) {
            throw new IllegalStateException("selectOptions must not be null/empty when responseType=SELECT");
        }
    }

}
