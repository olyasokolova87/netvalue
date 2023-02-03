package nz.netvalue.controller.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test location URI builder")
@SpringBootTest(classes = LocationHeaderBuilder.class)
class LocationHeaderBuilderTest {

    private static final long ENTITY_ID = 1L;

    @Autowired
    private LocationHeaderBuilder sut;

    @Test
    @DisplayName("Should return URI with Entity ID")
    void shouldReturnUriWithEntityId() throws URISyntaxException {
        URI actual = sut.build(ENTITY_ID);
        URI expected = new URI("http://localhost/" + ENTITY_ID);

        assertEquals(expected, actual);
    }
}