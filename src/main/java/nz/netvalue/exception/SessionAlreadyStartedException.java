package nz.netvalue.exception;

/**
 * Throws when charging session with same vehicle and RFID tag already started
 */
public class SessionAlreadyStartedException extends RuntimeException {

    public SessionAlreadyStartedException(String message) {
        super(message);
    }
}
