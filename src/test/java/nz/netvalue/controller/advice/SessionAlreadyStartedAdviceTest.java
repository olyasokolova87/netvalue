package nz.netvalue.controller.advice;

import nz.netvalue.domain.exception.SessionAlreadyStartedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test process SessionAlreadyStartedException in advice")
@SpringBootTest(classes = SessionAlreadyStartedAdvice.class)
class SessionAlreadyStartedAdviceTest {

    private static final String MESSAGE = "Resource not found";

    @Autowired
    private SessionAlreadyStartedAdvice sut;

    @Test
    @DisplayName("Return exception error message")
    void shouldGetErrorMessageFromException() {
        String errorMessage = sut.resourceNotFoundHandler(new SessionAlreadyStartedException(MESSAGE));

        assertEquals(MESSAGE, errorMessage);
    }
}