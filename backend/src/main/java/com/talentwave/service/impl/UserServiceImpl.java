package com.talentwave.service.impl;

import com.talentwave.service.UserService;
import com.talentwave.service.dto.UserCreateDTO;
import com.talentwave.service.dto.UserDTO;
import com.talentwave.service.dto.UserUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implémentation du service de gestion des utilisateurs.
 */
@Service
public class UserServiceImpl implements UserService {

    // Simulation d'une base de données en mémoire pour les besoins de démonstration
    private final Map<Long, UserDTO> userDatabase = new HashMap<>();

    public UserServiceImpl() {
        // Initialisation avec quelques utilisateurs de démonstration
        UserDTO user1 = new UserDTO();
        user1.setId(1L);
        user1.setUsername("johndoe");
        user1.setEmail("john.doe@talentwave.com");
        user1.setEnabled(true);
        userDatabase.put(1L, user1);

        UserDTO user2 = new UserDTO();
        user2.setId(2L);
        user2.setUsername("janesmith");
        user2.setEmail("jane.smith@talentwave.com");
        user2.setEnabled(true);
        userDatabase.put(2L, user2);
    }

    /**
     * Crée un nouvel utilisateur.
     *
     * @param userCreateDTO les données de création
     * @return l'utilisateur créé
     */
    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        // Simuler la création d'un nouvel utilisateur
        UserDTO newUser = new UserDTO();
        newUser.setId((long) (userDatabase.size() + 1));
        newUser.setUsername(userCreateDTO.getUsername());
        newUser.setEmail(userCreateDTO.getEmail());
        newUser.setEnabled(true);
        
        userDatabase.put(newUser.getId(), newUser);
        return newUser;
    }

    /**
     * Met à jour un utilisateur existant.
     *
     * @param id l'identifiant de l'utilisateur à mettre à jour
     * @param userUpdateDTO les données de mise à jour
     * @return l'utilisateur mis à jour
     */
    @Override
    public Optional<UserDTO> updateUser(Long id, UserUpdateDTO userUpdateDTO) {
        if (!userDatabase.containsKey(id)) {
            return Optional.empty();
        }
        
        UserDTO existingUser = userDatabase.get(id);
        // Mise à jour des champs pertinents
        if (userUpdateDTO.getEmail() != null) {
            existingUser.setEmail(userUpdateDTO.getEmail());
        }
        if (userUpdateDTO.getUsername() != null) {
            existingUser.setUsername(userUpdateDTO.getUsername());
        }
        
        userDatabase.put(id, existingUser);
        return Optional.of(existingUser);
    }

    /**
     * Récupère tous les utilisateurs avec pagination.
     *
     * @param pageable les informations de pagination
     * @return une page d'utilisateurs
     */
    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
        List<UserDTO> users = new ArrayList<>(userDatabase.values());
        
        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), users.size());
        
        if (start > users.size()) {
            return new PageImpl<>(new ArrayList<>(), pageable, users.size());
        }
        
        return new PageImpl<>(users.subList(start, end), pageable, users.size());
    }

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur
     * @return l'utilisateur trouvé
     */
    @Override
    public Optional<UserDTO> findOne(Long id) {
        if (!userDatabase.containsKey(id)) {
            return Optional.empty();
        }
        return Optional.of(userDatabase.get(id));
    }
    
    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     */
    @Override
    public void deleteUser(Long id) {
        userDatabase.remove(id);
    }
    
    /**
     * Vérifie si l'utilisateur courant est l'utilisateur spécifié.
     *
     * @param principal l'utilisateur courant
     * @param userId l'identifiant de l'utilisateur à vérifier
     * @return true si l'utilisateur courant est l'utilisateur spécifié
     */
    @Override
    public boolean isCurrentUser(Object principal, Long userId) {
        // Implémentation simplifiée pour la démonstration
        // Dans un cas réel, il faudrait extraire l'ID de l'utilisateur du principal
        return true;
    }
}
