package nz.netvalue.exception;

/**
 * Throws when entity not found
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
