package nz.netvalue.controller.advice;

import nz.netvalue.exception.SessionAlreadyStartedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test handle SessionAlreadyStartedException in advice")
@SpringBootTest(classes = SessionAlreadyStartedAdvice.class)
class SessionAlreadyStartedAdviceTest {

    private static final String MESSAGE = "Session already started";

    @Autowired
    private SessionAlreadyStartedAdvice sut;

    @Test
    @DisplayName("Should return error message from exception")
    void shouldGetErrorMessageFromException() {
        String errorMessage = sut.resourceNotFoundHandler(new SessionAlreadyStartedException(MESSAGE));

        assertEquals(MESSAGE, errorMessage);
    }
}