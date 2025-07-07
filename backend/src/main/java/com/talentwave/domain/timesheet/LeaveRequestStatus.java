package com.talentwave.domain.timesheet;

/**
 * Énumération des statuts possibles pour une demande de congé.
 * Cette énumération est utilisée dans le système pour suivre l'état des demandes de congé
 * tout au long de leur cycle de vie.
 */
public enum LeaveRequestStatus {
    /**
     * La demande a été soumise mais n'a pas encore été examinée
     */
    PENDING,
    
    /**
     * La demande a été examinée et approuvée
     */
    APPROVED,
    
    /**
     * La demande a été examinée et rejetée
     */
    REJECTED,
    
    /**
     * La demande a été annulée par l'employé avant ou après approbation
     */
    CANCELLED
}
