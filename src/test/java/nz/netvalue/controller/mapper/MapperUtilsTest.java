package nz.netvalue.controller.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Test mapper utils")
@SpringBootTest(classes = MapperUtils.class)
class MapperUtilsTest {

    @Autowired
    private MapperUtils sut;

    @Test
    @DisplayName("Should map UUID to String correctly")
    void shouldMapUUIDToString() {
        String expected = "3d2b21cb-64ae-4991-b631-bfa7a388f01c";
        String actual = sut.map(UUID.fromString(expected));

        assertEquals(expected, actual);
    }
}