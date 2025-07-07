package com.talentwave.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Gestionnaire global des exceptions pour l'application.
 * Convertit les exceptions en réponses HTTP standardisées.
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Gère les exceptions métier TalentWaveException.
     */
    @ExceptionHandler(TalentWaveException.class)
    public ResponseEntity<ApiError> handleTalentWaveException(
            TalentWaveException ex, HttpServletRequest request) {
        
        logger.error("Exception métier: {}", ex.getMessage(), ex);
        
        ApiError apiError = new ApiError(
                ex.getErrorCode(),
                ex.getMessage(),
                ex.getCause() != null ? ex.getCause().getMessage() : null,
                request.getRequestURI()
        );
        
        return new ResponseEntity<>(apiError, ex.getStatus());
    }

    /**
     * Gère les exceptions de validation des arguments de méthode.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationExceptions(
            MethodArgumentNotValidException ex, HttpServletRequest request) {
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        String details = errors.entrySet().stream()
                .map(entry -> entry.getKey() + ": " + entry.getValue())
                .collect(Collectors.joining(", "));
        
        logger.error("Erreur de validation: {}", details);
        
        ApiError apiError = new ApiError(
                "VALIDATION_ERROR",
                "Erreur de validation des données",
                details,
                request.getRequestURI()
        );
        
        return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
    }

    /**
     * Gère les exceptions d'authentification.
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ApiError> handleAuthenticationException(
            BadCredentialsException ex, HttpServletRequest request) {
        
        logger.error("Erreur d'authentification: {}", ex.getMessage());
        
        ApiError apiError = new ApiError(
                "AUTHENTICATION_ERROR",
                "Identifiants invalides",
                ex.getMessage(),
                request.getRequestURI()
        );
        
        return new ResponseEntity<>(apiError, HttpStatus.UNAUTHORIZED);
    }

    /**
     * Gère les exceptions d'accès refusé.
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiError> handleAccessDeniedException(
            AccessDeniedException ex, HttpServletRequest request) {
        
        logger.error("Accès refusé: {}", ex.getMessage());
        
        ApiError apiError = new ApiError(
                "ACCESS_DENIED",
                "Accès refusé",
                ex.getMessage(),
                request.getRequestURI()
        );
        
        return new ResponseEntity<>(apiError, HttpStatus.FORBIDDEN);
    }

    /**
     * Gère toutes les autres exceptions non traitées.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGlobalException(
            Exception ex, HttpServletRequest request) {
        
        logger.error("Exception non traitée: {}", ex.getMessage(), ex);
        
        ApiError apiError = new ApiError(
                "INTERNAL_SERVER_ERROR",
                "Une erreur interne s'est produite",
                ex.getMessage(),
                request.getRequestURI()
        );
        
        return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
