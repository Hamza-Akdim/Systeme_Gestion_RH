package com.talentwave.domain.offer;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author akdim
 */

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "task_mission")
public class TaskMission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

//    @ManyToOne
//    @JoinColumn(name = "jobOffer_id", referencedColumnName = "id", nullable = false)
//    private JobOffer jobOffer;
}
