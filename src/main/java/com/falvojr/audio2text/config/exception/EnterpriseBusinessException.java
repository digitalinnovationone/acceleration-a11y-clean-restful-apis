package com.falvojr.audio2text.config.exception;

/**
 * Custom exception class for business rule violations occurring within the domain entities.
 * Represents conditions where enterprise-level business rules or constraints are breached. <br>
 * <br>
 * Usage: <br>
 * - Thrown by entities to indicate a violation of critical business rules or constraints. <br>
 * - Helps ensure that entities maintain valid states and business logic integrity.
 *
 * @author falvojr
 */
public class EnterpriseBusinessException extends RuntimeException {
    public EnterpriseBusinessException(String message) {
        super(message);
    }
}
