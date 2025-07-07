package com.talentwave.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Exception de base pour toutes les exceptions métier de l'application.
 * Contient un code d'erreur, un message et un statut HTTP.
 */
@Getter
public class TalentWaveException extends RuntimeException {
    
    private final String errorCode;
    private final HttpStatus status;
    
    public TalentWaveException(String message, String errorCode, HttpStatus status) {
        super(message);
        this.errorCode = errorCode;
        this.status = status;
    }
    
    public TalentWaveException(String message, String errorCode, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
        this.status = status;
    }
}
