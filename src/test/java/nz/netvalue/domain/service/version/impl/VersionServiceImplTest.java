package nz.netvalue.domain.service.version.impl;

import nz.netvalue.domain.model.version.Version;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Test service app version")
@SpringBootTest(classes = VersionServiceImpl.class)
class VersionServiceImplTest {

    @Autowired
    private VersionServiceImpl sut;

    @MockBean
    private Version version;

    @Test
    @DisplayName("Should return correct database and application versions")
    void shouldGetVersion() {
        String expectedApp = "1.0";
        String expectedDb = "2.0";
        when(version.getApplication()).thenReturn(expectedApp);
        when(version.getDatabase()).thenReturn(expectedDb);

        Version actual = sut.get();
        assertEquals(expectedApp, actual.getApplication());
        assertEquals(expectedDb, actual.getDatabase());
    }
}