package nz.netvalue.controller.advice;

import nz.netvalue.domain.exception.SessionAlreadyStartedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class SessionAlreadyStartedAdvice {

    @ResponseBody
    @ExceptionHandler(SessionAlreadyStartedException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String resourceNotFoundHandler(SessionAlreadyStartedException ex) {
        return ex.getMessage();
    }
}
