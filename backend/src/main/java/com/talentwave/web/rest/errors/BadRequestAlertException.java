package com.talentwave.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

/**
 * Exception thrown when a bad request is detected.
 */
public class BadRequestAlertException extends ResponseStatusException {

    private static final long serialVersionUID = 1L;
    
    private final String entityName;
    private final String errorKey;

    /**
     * Constructs a new bad request alert exception with the specified detail message.
     *
     * @param defaultMessage the detail message
     * @param entityName the name of the entity having the issue
     * @param errorKey the error key for internationalization
     */
    public BadRequestAlertException(String defaultMessage, String entityName, String errorKey) {
        super(HttpStatus.BAD_REQUEST, defaultMessage);
        this.entityName = entityName;
        this.errorKey = errorKey;
    }

    /**
     * Gets the entity name.
     *
     * @return the entity name
     */
    public String getEntityName() {
        return entityName;
    }

    /**
     * Gets the error key.
     *
     * @return the error key
     */
    public String getErrorKey() {
        return errorKey;
    }
}
