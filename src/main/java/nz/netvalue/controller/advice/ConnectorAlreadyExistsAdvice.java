package nz.netvalue.controller.advice;

import nz.netvalue.exception.ConnectorAlreadyCreatedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Handle ConnectorAlreadyCreatedException
 */
@ControllerAdvice
public class ConnectorAlreadyExistsAdvice {

    @ResponseBody
    @ExceptionHandler(ConnectorAlreadyCreatedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String resourceNotFoundHandler(ConnectorAlreadyCreatedException ex) {
        return ex.getMessage();
    }
}
