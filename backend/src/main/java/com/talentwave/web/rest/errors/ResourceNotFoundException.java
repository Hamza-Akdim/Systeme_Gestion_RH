package com.talentwave.web.rest.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a resource is not found.
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /**
     * Constructs a new resource not found exception with the specified detail message.
     *
     * @param message the detail message
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Constructs a new resource not found exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause the cause
     */
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructs a new resource not found exception for a specific resource type and identifier.
     *
     * @param resourceType the type of resource that was not found
     * @param resourceId the identifier of the resource that was not found
     * @return a new resource not found exception
     */
    public static ResourceNotFoundException create(String resourceType, Object resourceId) {
        return new ResourceNotFoundException(resourceType + " not found with id: " + resourceId);
    }
}
