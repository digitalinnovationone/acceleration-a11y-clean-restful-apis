package com.falvojr.audio2text.config.exception;

/**
 * Custom exception class for handling business logic errors within use cases.
 * Used to indicate application-specific issues or rule violations in the application layer. <br>
 * <br>
 * Usage: <br>
 * - Thrown by use cases to signal failures or exceptional conditions specific to application logic. <br>
 * - Useful for encapsulating errors that are not directly related to the core business logic of domain entities.
 *
 * @author falvojr
 */
public class ApplicationBusinessException extends RuntimeException {
    public ApplicationBusinessException(String message) {
        super(message);
    }
}
