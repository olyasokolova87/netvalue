package nz.netvalue.controller;

import nz.netvalue.config.SecurityConfiguration;
import nz.netvalue.controller.mapper.VersionMapper;
import nz.netvalue.controller.model.VersionResponse;
import nz.netvalue.domain.model.version.Version;
import nz.netvalue.domain.service.version.VersionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Test version controller")
@WebMvcTest(controllers = {VersionsApiController.class, VersionsApiDelegate.class})
@Import(SecurityConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
public class VersionsApiControllerTest {

    private static final String VERSION = "1.0";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private VersionService versionService;
    @MockBean
    private VersionMapper versionMapper;

    @Test
    @DisplayName("Should return versions")
    void shouldReturnVersions() throws Exception {
        when(versionService.get()).thenReturn(createVersion());
        when(versionMapper.toResponse(any())).thenReturn(createResponse());
        mockMvc.perform(MockMvcRequestBuilders.get("/versions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.database").value(VERSION))
                .andExpect(jsonPath("$.application").value(VERSION))
                .andExpect(header().string("Cache-Control", "max-age=86400, must-revalidate, public"));
    }

    private static Version createVersion() {
        Version version = new Version();
        version.setDatabase(VERSION);
        version.setApplication(VERSION);
        return version;
    }

    private static VersionResponse createResponse() {
        VersionResponse response = new VersionResponse();
        response.setDatabase(VERSION);
        response.setApplication(VERSION);
        return response;
    }
}
