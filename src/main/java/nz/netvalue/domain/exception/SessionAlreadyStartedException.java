package nz.netvalue.domain.exception;

public class SessionAlreadyStartedException extends RuntimeException {

    public SessionAlreadyStartedException(String message) {
        super(message);
    }
}
