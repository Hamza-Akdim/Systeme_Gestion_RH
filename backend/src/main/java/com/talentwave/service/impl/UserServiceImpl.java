package com.talentwave.service.impl;

import com.talentwave.domain.User;
import com.talentwave.domain.enumeration.Role;
import com.talentwave.repository.UserRepository;
import com.talentwave.service.UserService;
import com.talentwave.service.dto.UserCreateDTO;
import com.talentwave.service.dto.UserDTO;
import com.talentwave.service.dto.UserUpdateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Crée un nouvel utilisateur.
     *
     * @param userCreateDTO les données de création
     * @return l'utilisateur créé
     */

    @Override
    public UserDTO createUser(UserCreateDTO userCreateDTO) {
        String hashedPassword = passwordEncoder.encode(userCreateDTO.getPassword());

        Role role = userCreateDTO.getRole() != null
                ? userCreateDTO.getRole()
                : Role.CONSULTANT;

        User user = new User(
                userCreateDTO.getFirstname(),
                userCreateDTO.getLastname(),
                userCreateDTO.getEmail(),
                hashedPassword,
                role.name()
        );

        if (userCreateDTO.getUsername() != null && !userCreateDTO.getUsername().isBlank()) {
            user.setUsername(userCreateDTO.getUsername());
        }else{
            user.setUsername(userCreateDTO.getEmail());

        }

        user = userRepository.save(user);

        return toDTO(user);
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
//        if (!userDatabase.containsKey(id)) {
//            return Optional.empty();
//        }
//
//        UserDTO existingUser = userDatabase.get(id);
//        // Mise à jour des champs pertinents
//        if (userUpdateDTO.getEmail() != null) {
//            existingUser.setEmail(userUpdateDTO.getEmail());
//        }
//        if (userUpdateDTO.getUsername() != null) {
//            existingUser.setUsername(userUpdateDTO.getUsername());
//        }
//
//        userDatabase.put(id, existingUser);
//        return Optional.of(existingUser);
        return null;
    }

    /**
     * Récupère tous les utilisateurs avec pagination.
     *
     * @param pageable les informations de pagination
     * @return une page d'utilisateurs
     */
    @Override
    public Page<UserDTO> findAll(Pageable pageable) {
//        List<UserDTO> users = new ArrayList<>(userDatabase.values());
//
//        int start = (int) pageable.getOffset();
//        int end = Math.min((start + pageable.getPageSize()), users.size());
//
//        if (start > users.size()) {
//            return new PageImpl<>(new ArrayList<>(), pageable, users.size());
//        }
//
//        return new PageImpl<>(users.subList(start, end), pageable, users.size());
        return null;
    }

    /**
     * Récupère un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur
     * @return l'utilisateur trouvé
     */
    @Override
    public Optional<UserDTO> findOne(Long id) {

        return null;
    }
    
    /**
     * Supprime un utilisateur par son identifiant.
     *
     * @param id l'identifiant de l'utilisateur à supprimer
     */
    @Override
    public void deleteUser(Long id) {
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("User with ID " + id + " not found");
        }
        userRepository.deleteById(id);
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

    private UserDTO toDTO(User user) {
        if (user == null) {
            return null;
        }

        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setFirstname(user.getFirstName());
        dto.setLastname(user.getLastName());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(Role.valueOf(user.getRole()));
        dto.setEnabled(user.isActive());

        return dto;
    }
}
