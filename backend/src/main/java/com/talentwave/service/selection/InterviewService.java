package com.talentwave.service.selection;

import com.talentwave.service.dto.selection.InterviewDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface pour la gestion des entretiens.
 */
public interface InterviewService {

    /**
     * Sauvegarde un entretien.
     *
     * @param interviewDTO l'entretien à sauvegarder
     * @return l'entretien sauvegardé
     */
    InterviewDTO save(InterviewDTO interviewDTO);

    /**
     * Met à jour partiellement un entretien.
     *
     * @param interviewDTO l'entretien à mettre à jour
     * @return l'entretien mis à jour
     */
    Optional<InterviewDTO> partialUpdate(InterviewDTO interviewDTO);

    /**
     * Récupère tous les entretiens.
     *
     * @param pageable les informations de pagination
     * @return une page d'entretiens
     */
    Page<InterviewDTO> findAll(Pageable pageable);

    /**
     * Récupère tous les entretiens pour une candidature donnée.
     *
     * @param applicationId l'identifiant de la candidature
     * @return une liste d'entretiens
     */
    List<InterviewDTO> findAllByApplicationId(Long applicationId);

    /**
     * Récupère tous les entretiens pour un consultant donné.
     *
     * @param consultantId l'identifiant du consultant
     * @return une liste d'entretiens
     */
    List<InterviewDTO> findAllByConsultantId(Long consultantId);

    /**
     * Récupère un entretien par son identifiant.
     *
     * @param id l'identifiant de l'entretien
     * @return l'entretien
     */
    Optional<InterviewDTO> findOne(Long id);

    /**
     * Supprime un entretien par son identifiant.
     *
     * @param id l'identifiant de l'entretien
     */
    void delete(Long id);
}
