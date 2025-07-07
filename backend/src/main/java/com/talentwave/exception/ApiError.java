package com.talentwave.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDateTime;

/**
 * Structure standardisée pour les réponses d'erreur API.
 * Contient un timestamp, un code d'erreur, un message et des détails supplémentaires.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    
    private LocalDateTime timestamp;
    private String errorCode;
    private String message;
    private String details;
    private String path;
    
    public ApiError(String errorCode, String message, String details, String path) {
        this.timestamp = LocalDateTime.now();
        this.errorCode = errorCode;
        this.message = message;
        this.details = details;
        this.path = path;
    }
}
