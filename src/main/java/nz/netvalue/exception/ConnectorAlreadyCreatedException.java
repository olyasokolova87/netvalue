package nz.netvalue.exception;

/**
 * Throws when charge connector with number in charge point already exists
 */
public class ConnectorAlreadyCreatedException extends RuntimeException {

    public ConnectorAlreadyCreatedException(String message) {
        super(message);
    }
}
