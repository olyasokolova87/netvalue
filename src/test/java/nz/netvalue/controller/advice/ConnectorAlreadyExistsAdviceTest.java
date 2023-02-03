package nz.netvalue.controller.advice;

import nz.netvalue.exception.ConnectorAlreadyCreatedException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test handle ConnectorAlreadyCreatedException in advice")
@SpringBootTest(classes = ConnectorAlreadyExistsAdvice.class)
class ConnectorAlreadyExistsAdviceTest {

    private static final String MESSAGE = "Connector already exists";

    @Autowired
    private ConnectorAlreadyExistsAdvice sut;

    @Test
    @DisplayName("Should return error message from exception")
    void shouldGetErrorMessageFromException() {
        String errorMessage = sut.resourceNotFoundHandler(new ConnectorAlreadyCreatedException(MESSAGE));

        assertEquals(MESSAGE, errorMessage);
    }
}