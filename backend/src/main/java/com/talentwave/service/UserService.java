package com.talentwave.service;

import com.talentwave.service.dto.UserCreateDTO;
import com.talentwave.service.dto.UserDTO;
import com.talentwave.service.dto.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Optional;

/**
 * Service Interface pour la gestion des utilisateurs.
 */
public interface UserService {

    /**
     * Crée un nouvel utilisateur.
     *
     * @param userCreateDTO les données de création
     * @return l'utilisateur créé
     */
    UserDTO createUser(UserCreateDTO userCreateDTO);

    /**
     * Met à jour un utilisateur existant.
     *
     * @param id l'identifiant de l'utilisateur à mettre à jour
     * @param userUpdateDTO les données de mise à jour
     * @return l'utilisateur mis à jour
     */
    Optional<UserDTO> updateUser(Long id, UserUpdateDTO userUpdateDTO);

    /**
     * Récupère tous les utilisateurs avec pagination.
     *
     * @param pageable les informations de pagination
     * @return une page d'utilisateurs
     */
    Page<UserDTO> findAll(Pageable pageable);

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur
     * @return l'utilisateur trouvé
     */
    Optional<UserDTO> findOne(Long id);
    
    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     */
    void deleteUser(Long id);
    
    /**
     * Vérifie si l'utilisateur courant est l'utilisateur spécifié.
     *
     * @param principal l'utilisateur courant
     * @param userId l'identifiant de l'utilisateur à vérifier
     * @return true si l'utilisateur courant est l'utilisateur spécifié
     */
    boolean isCurrentUser(Object principal, Long userId);
}
