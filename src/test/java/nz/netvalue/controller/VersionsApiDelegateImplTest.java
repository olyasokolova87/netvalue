package nz.netvalue.controller;

import nz.netvalue.controller.dto.VersionResponseDto;
import nz.netvalue.controller.mapper.VersionMapper;
import nz.netvalue.domain.model.version.Version;
import nz.netvalue.domain.service.VersionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@DisplayName("Test delegate for version API")
@SpringBootTest(classes = VersionsApiDelegateImpl.class)
class VersionsApiDelegateImplTest {

    @Autowired
    private VersionsApiDelegateImpl sut;

    @MockBean
    private VersionService versionService;

    @MockBean
    private VersionMapper versionMapper;

    @Test
    @DisplayName("Get version API should return Version obejct")
    void shouldGetVersionResponse() {
        when(versionService.get()).thenReturn(new Version());
        when(versionMapper.toResponse(any())).thenReturn(new VersionResponseDto());
        ResponseEntity<VersionResponseDto> actual = sut.getVersion();

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
    }
}