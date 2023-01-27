package nz.netvalue.controller.mapper;

import nz.netvalue.controller.dto.VersionResponseDto;
import nz.netvalue.domain.model.version.Version;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Test map version information to response")
@SpringBootTest(classes = VersionMapperImpl.class)
class VersionMapperTest {

    private static final String EXPECTED_DB = "2.0";
    private static final String EXPECTED_APP = "1.0";

    @Autowired
    private VersionMapperImpl sut;

    @Test
    @DisplayName("When mapped dto fill correctly")
    void shouldMapVersionToResponse() {
        Version version = createVersion();

        VersionResponseDto actual = sut.toResponse(version);
        assertNotNull(actual);
        assertEquals(EXPECTED_APP, actual.getApplication());
        assertEquals(EXPECTED_DB, actual.getDatabase());
    }

    private static Version createVersion() {
        Version version = new Version();
        version.setApplication(EXPECTED_APP);
        version.setDatabase(EXPECTED_DB);
        return version;
    }

}