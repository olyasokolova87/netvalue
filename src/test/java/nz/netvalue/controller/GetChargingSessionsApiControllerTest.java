package nz.netvalue.controller;

import nz.netvalue.config.SecurityConfiguration;
import nz.netvalue.controller.mapper.ChargingSessionMapper;
import nz.netvalue.controller.model.ChargingSessionResponse;
import nz.netvalue.controller.utils.LocationHeaderBuilder;
import nz.netvalue.domain.service.session.EndSessionService;
import nz.netvalue.domain.service.session.GetSessionService;
import nz.netvalue.domain.service.session.StartSessionService;
import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.exception.SessionAlreadyStartedException;
import nz.netvalue.persistence.model.ChargingSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Test charging session controller")
@WebMvcTest(controllers = {ChargingSessionsApiController.class, ChargingSessionsApiDelegate.class})
@Import(SecurityConfiguration.class)
@MockBean(JpaMetamodelMappingContext.class)
public class GetChargingSessionsApiControllerTest {

    public static final String CREATED_URI = "localhost:8080/1";
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GetSessionService getSessionService;
    @MockBean
    private StartSessionService startSessionService;
    @MockBean
    private EndSessionService endSessionService;
    @MockBean
    private ChargingSessionMapper sessionMapper;
    @MockBean
    private LocationHeaderBuilder locationHeaderBuilder;

    @Nested
    @DisplayName("Test get session")
    class GetSessionTest {
        @Test
        @DisplayName("Should return 401")
        void shouldReturnUnauthorized() throws Exception {
            mockMvc.perform(get("/charging-sessions"))
                    .andExpect(status().isUnauthorized());
        }


        @Test
        @WithMockUser
        @DisplayName("Should return 403")
        void shouldReturnForbidden() throws Exception {
            mockMvc.perform(get("/charging-sessions"))
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        @DisplayName("Should return 200 and list of sessions if authorized")
        void shouldReturnSessions() throws Exception {
            when(getSessionService.getChargeSessions(null, null))
                    .thenReturn(createSessions());
            when(sessionMapper.toResponseList(any())).thenReturn(createResponses());
            mockMvc.perform(get("/charging-sessions"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.[*]", hasSize(1)))
                    .andExpect(jsonPath("$.[0].id").value("1"));
        }

        @Test
        @WithMockUser(roles = "ADMIN")
        @DisplayName("Should return 200 and list of sessions for period if authorized")
        void shouldReturnSessionsForPeriod() throws Exception {
            when(getSessionService.getChargeSessions(null, null))
                    .thenReturn(createSessions());
            when(sessionMapper.toResponseList(any())).thenReturn(createResponses());
            mockMvc.perform(get("/charging-sessions")
                            .param("dateFrom", "2020-01-01")
                            .param("dateTo", "2020-01-02"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.[*]", hasSize(1)))
                    .andExpect(jsonPath("$.[0].id").value("1"));
        }
    }

    @Nested
    @DisplayName("Test start session")
    class StartSessionTest {

        @Test
        @DisplayName("Should return 401")
        void shouldReturnUnauthorized() throws Exception {
            mockMvc.perform(post("/charging-sessions"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403")
        void shouldReturnForbidden() throws Exception {
            mockMvc.perform(postStartSession())
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(roles = "CUSTOMER")
        @DisplayName("Should return 400")
        void shouldReturnBadRequest() throws Exception {
            mockMvc.perform(post("/charging-sessions")
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("{}"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser(roles = "CUSTOMER")
        @DisplayName("Should return 404")
        void shouldReturnNotFoundResourceNotFound() throws Exception {
            when(startSessionService.startSession(any())).thenThrow(ResourceNotFoundException.class);
            mockMvc.perform(postStartSession())
                    .andExpect(status().isNotFound());
        }

        @Test
        @WithMockUser(roles = "CUSTOMER")
        @DisplayName("Should return 409")
        void shouldReturnConflictWhenSessionAlreadyStarted() throws Exception {
            when(startSessionService.startSession(any())).thenThrow(SessionAlreadyStartedException.class);
            mockMvc.perform(postStartSession())
                    .andExpect(status().isConflict());
        }

        @Test
        @WithMockUser(roles = "CUSTOMER")
        @DisplayName("Should return 201 when start session if authorized")
        void shouldStartSession() throws Exception {
            when(startSessionService.startSession(any())).thenReturn(createSession());
            when(locationHeaderBuilder.build(any())).thenReturn(URI.create(CREATED_URI));
            mockMvc.perform(postStartSession())
                    .andExpect(status().isCreated())
                    .andExpect(header().string("Location", CREATED_URI));
        }

        private MockHttpServletRequestBuilder postStartSession() {
            return post("/charging-sessions")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content("{" +
                            "  \"rfIdTagNumber\": \"9382134b-46f1-437f-b581-49c533a49661\"," +
                            "  \"vehicleRegistrationPlate\": \"343-738\"," +
                            "  \"pointSerialNumber\": \"number1\"," +
                            "  \"connectorNumber\": 1," +
                            "  \"startTime\": \"2022-01-01T12:00:00\"" +
                            "}");
        }
    }

    @Nested
    @DisplayName("Test end session")
    class EndSessionTest {

        @Test
        @DisplayName("Should return 401")
        void shouldReturnUnauthorized() throws Exception {
            mockMvc.perform(put("/charging-sessions"))
                    .andExpect(status().isUnauthorized());
        }

        @Test
        @WithMockUser
        @DisplayName("Should return 403")
        void shouldReturnForbidden() throws Exception {
            mockMvc.perform(putEndSession())
                    .andExpect(status().isForbidden());
        }

        @Test
        @WithMockUser(roles = "CUSTOMER")
        @DisplayName("Should return 400")
        void shouldReturnBadRequest() throws Exception {
            mockMvc.perform(put("/charging-sessions/{sessionId}", 1L)
                            .contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)
                            .content("{\"endTime\": \"2022-01-55T12:30:00\"}"))
                    .andExpect(status().isBadRequest());
        }

        @Test
        @WithMockUser(roles = "CUSTOMER")
        @DisplayName("Should return 404")
        void shouldReturnNotFoundSessionNotFound() throws Exception {
            doThrow(ResourceNotFoundException.class).when(endSessionService).endSession(any(), any());
            mockMvc.perform(putEndSession())
                    .andExpect(status().isNotFound());
        }

        @Test
        @WithMockUser(roles = "CUSTOMER")
        @DisplayName("Should return 200 when end session if authorized")
        void shouldStartSession() throws Exception {
            mockMvc.perform(putEndSession())
                    .andExpect(status().isOk());
        }

        private MockHttpServletRequestBuilder putEndSession() {
            return put("/charging-sessions/{sessionId}", 1L)
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .content("{" +
                            "  \"endTime\": \"2022-01-01T12:30:00\"," +
                            "  \"meterValue\": 15" +
                            "}");
        }
    }

    private static List<ChargingSession> createSessions() {
        return List.of(createSession());
    }

    private static ChargingSession createSession() {
        ChargingSession session = new ChargingSession();
        session.setId(1L);
        session.setLastModifiedDate(LocalDateTime.now());
        session.setCreatedDate(LocalDateTime.now());
        return session;
    }

    private static List<ChargingSessionResponse> createResponses() {
        ChargingSessionResponse response = new ChargingSessionResponse();
        response.setId(1L);
        return List.of(response);
    }
}
