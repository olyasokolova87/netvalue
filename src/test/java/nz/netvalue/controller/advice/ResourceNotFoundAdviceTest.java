package nz.netvalue.controller.advice;

import nz.netvalue.domain.exception.ResourceNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test process exceptions in advice")
@SpringBootTest(classes = ResourceNotFoundAdvice.class)
class ResourceNotFoundAdviceTest {

    private static final String MESSAGE = "Resource not found";

    @Autowired
    private ResourceNotFoundAdvice sut;

    @Test
    @DisplayName("Return exception error message")
    void shouldGetErrorMessageFromException() {
        String errorMessage = sut.resourceNotFoundHandler(new ResourceNotFoundException(MESSAGE));

        assertEquals(MESSAGE, errorMessage);
    }
}