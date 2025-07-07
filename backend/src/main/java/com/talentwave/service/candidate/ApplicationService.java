package com.talentwave.service.candidate;

import com.talentwave.service.dto.candidate.ApplicationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

/**
 * Service Interface pour la gestion des candidatures.
 */
public interface ApplicationService {

    /**
     * Enregistre une candidature.
     *
     * @param applicationDTO les données de la candidature à enregistrer
     * @return la candidature enregistrée
     */
    ApplicationDTO save(ApplicationDTO applicationDTO);

    /**
     * Met à jour partiellement une candidature.
     *
     * @param applicationDTO les données partielles de la candidature à mettre à jour
     * @return la candidature mise à jour
     */
    Optional<ApplicationDTO> partialUpdate(ApplicationDTO applicationDTO);

    /**
     * Récupère toutes les candidatures avec pagination.
     *
     * @param pageable les informations de pagination
     * @return une page de candidatures
     */
    Page<ApplicationDTO> findAll(Pageable pageable);

    /**
     * Récupère une candidature par son identifiant.
     *
     * @param id l'identifiant de la candidature
     * @return la candidature
     */
    Optional<ApplicationDTO> findOne(Long id);

    /**
     * Supprime une candidature par son identifiant.
     *
     * @param id l'identifiant de la candidature
     */
    void delete(Long id);

    /**
     * Récupère toutes les candidatures pour un candidat donné avec pagination.
     *
     * @param candidateId l'identifiant du candidat
     * @param pageable les informations de pagination
     * @return une page de candidatures
     */
    Page<ApplicationDTO> findAllByCandidateId(Long candidateId, Pageable pageable);

    /**
     * Récupère toutes les candidatures pour une offre d'emploi donnée avec pagination.
     *
     * @param jobOfferId l'identifiant de l'offre d'emploi
     * @param pageable les informations de pagination
     * @return une page de candidatures
     */
    Page<ApplicationDTO> findAllByJobOfferId(Long jobOfferId, Pageable pageable);
    
    /**
     * Méthode de compatibilité pour les anciens contrôleurs.
     * Récupère toutes les candidatures pour un candidat donné sans pagination.
     *
     * @param candidateId l'identifiant du candidat
     * @return une liste de candidatures
     */
    Iterable<ApplicationDTO> findAllByCandidateId(Long candidateId);
    
    /**
     * Méthode de compatibilité pour les anciens contrôleurs.
     * Récupère toutes les candidatures pour une offre d'emploi donnée sans pagination.
     *
     * @param jobOfferId l'identifiant de l'offre d'emploi
     * @return une liste de candidatures
     */
    Iterable<ApplicationDTO> findAllByJobOfferId(Long jobOfferId);
}
