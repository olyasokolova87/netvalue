package nz.netvalue.controller.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test build location URI")
@SpringBootTest(classes = LocationBuilder.class)
class LocationBuilderTest {

    private static final long ENTITY_ID = 1L;

    @Autowired
    private LocationBuilder sut;

    @Test
    @DisplayName("Return URI with Entity ID")
    void shouldReturnUriWithEntityId() throws URISyntaxException {
        URI actual = sut.build(ENTITY_ID);
        URI expected = new URI("http://localhost/" + ENTITY_ID);

        assertEquals(expected, actual);
    }
}