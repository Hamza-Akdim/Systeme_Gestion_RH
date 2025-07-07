package com.talentwave.domain;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

/**
 * Entité représentant un utilisateur dans le système.
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@Data
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Column(name = "username")
    private String username;
    
    /**
     * Getter pour le nom d'utilisateur.
     * Si le nom d'utilisateur n'est pas défini, retourne l'email comme nom d'utilisateur par défaut.
     *
     * @return le nom d'utilisateur ou l'email si le nom d'utilisateur est null
     */
    public String getUsername() {
        return username != null ? username : email;
    }
    
    /**
     * Setter pour le nom d'utilisateur.
     *
     * @param username le nom d'utilisateur à définir
     */
    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password_hash", nullable = false)
    private String passwordHash;
    
    /**
     * Getter pour le mot de passe hashé.
     *
     * @return le hash du mot de passe
     */
    public String getPassword() {
        return passwordHash;
    }
    
    /**
     * Setter pour le mot de passe hashé.
     *
     * @param password le hash du mot de passe à définir
     */
    public void setPassword(String password) {
        this.passwordHash = password;
    }
    
    /**
     * Vérifie si l'utilisateur est actif.
     *
     * @return true si l'utilisateur est actif, false sinon
     */
    public boolean isEnabled() {
        return active;
    }

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profile_picture_url")
    private String profilePictureUrl;

    @Column(name = "address")
    private String address;

    @Column(name = "city")
    private String city;

    @Column(name = "country")
    private String country;

    @Column(name = "postal_code")
    private String postalCode;

    @Column(name = "biography", columnDefinition = "TEXT")
    private String biography;

    @Column(name = "job_title")
    private String jobTitle;

    @Column(name = "department")
    private String department;

    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

    /**
     * Constructeur avec paramètres essentiels.
     *
     * @param firstName le prénom
     * @param lastName le nom
     * @param email l'email
     * @param passwordHash le hash du mot de passe
     * @param role le rôle
     */
    public User(String firstName, String lastName, String email, String passwordHash, String role) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.active = true;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }
}
