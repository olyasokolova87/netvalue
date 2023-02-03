package nz.netvalue.controller.advice;

import nz.netvalue.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test handle ResourceNotFoundException in advice")
@SpringBootTest(classes = ResourceNotFoundAdvice.class)
class ResourceNotFoundAdviceTest {

    private static final String MESSAGE = "Resource not found";

    @Autowired
    private ResourceNotFoundAdvice sut;

    @Test
    @DisplayName("Should return error message from exception")
    void shouldGetErrorMessageFromException() {
        String errorMessage = sut.resourceNotFoundHandler(new ResourceNotFoundException(MESSAGE));

        assertEquals(MESSAGE, errorMessage);
    }
}