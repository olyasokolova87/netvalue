package nz.netvalue.controller;

import nz.netvalue.config.SecurityConfiguration;
import nz.netvalue.controller.utils.LocationHeaderBuilder;
import nz.netvalue.domain.service.connector.CreateConnectorService;
import nz.netvalue.exception.ConnectorAlreadyCreatedException;
import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.ChargeConnector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URI;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Test charging points controller")
@WebMvcTest(controllers = {ChargePointsApiController.class, ChargePointsApiDelegate.class})
@Import(SecurityConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
public class ChargePointsApiControllerTest {

    private static final long CONNECTOR_ID = 1L;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CreateConnectorService connectorService;
    @MockBean
    private LocationHeaderBuilder locationHeaderBuilder;

    @Test
    @WithAnonymousUser
    @DisplayName("Should return 401")
    void shouldReturnUnauthorized() throws Exception {
        mockMvc.perform(postConnector())
                .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser
    @DisplayName("Should return 403")
    void shouldReturnForbidden() throws Exception {
        mockMvc.perform(postConnector())
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return 400")
    void shouldReturnBadRequest() throws Exception {
        mockMvc.perform(post("/charge-points/{serialNumber}/connectors", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return 404")
    void shouldReturnResourceNotFoundWhenPointNotFound() throws Exception {
        when(connectorService.addConnectorToPoint(any(), any())).thenThrow(ResourceNotFoundException.class);
        mockMvc.perform(postConnector())
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return 409")
    void shouldReturnConflictWhenConnectorAlreadyExist() throws Exception {
        when(connectorService.addConnectorToPoint(any(), any())).thenThrow(ConnectorAlreadyCreatedException.class);
        mockMvc.perform(postConnector())
                .andExpect(status().isConflict());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    @DisplayName("Should return 201 whe add connector if authorized")
    void shouldAddConnector() throws Exception {
        when(connectorService.addConnectorToPoint(any(), any())).thenReturn(createConnector());
        when(locationHeaderBuilder.build(CONNECTOR_ID)).thenReturn(URI.create("localhost:8080/1"));
        mockMvc.perform(postConnector())
                .andExpect(status().isCreated());
    }

    private static MockHttpServletRequestBuilder postConnector() {
        return post("/charge-points/{serialNumber}/connectors", "1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content("{\"connectorNumber\": 13}");
    }

    private static ChargeConnector createConnector() {
        ChargeConnector connector = new ChargeConnector();
        connector.setId(CONNECTOR_ID);
        return connector;
    }
}
