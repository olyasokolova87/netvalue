package nz.netvalue.controller.impl;

import nz.netvalue.controller.mapper.ChargingSessionMapper;
import nz.netvalue.controller.model.ChargingSessionResponse;
import nz.netvalue.controller.model.EndSessionRequest;
import nz.netvalue.controller.model.StartSessionRequest;
import nz.netvalue.controller.utils.LocationHeaderBuilder;
import nz.netvalue.domain.service.session.EndSessionService;
import nz.netvalue.domain.service.session.GetSessionService;
import nz.netvalue.domain.service.session.StartSessionService;
import nz.netvalue.persistence.model.ChargingSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

import static java.util.List.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Test delegate for session API")
@SpringBootTest(classes = ChargingSessionApiDelegateImpl.class)
class ChargingSessionApiDelegateImplTest {

    private static final String SOME_URI = "some/uri";

    @Autowired
    private ChargingSessionApiDelegateImpl sut;

    @MockBean
    private GetSessionService getSessionService;

    @MockBean
    private StartSessionService startSessionService;

    @MockBean
    private EndSessionService endSessionService;

    @MockBean
    private ChargingSessionMapper mapper;

    @MockBean
    private LocationHeaderBuilder locationHeaderBuilder;

    @Test
    @DisplayName("Should return list of charging sessions")
    void shouldReturnSessionListResponse() {
        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();
        when(getSessionService.getChargeSessions(dateFrom, dateTo))
                .thenReturn(of(new ChargingSession()));
        when(mapper.toResponseList(anyList()))
                .thenReturn(of(new ChargingSessionResponse()));

        ResponseEntity<List<ChargingSessionResponse>> actual = sut.getChargeSessions(dateFrom, dateTo);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals(1, actual.getBody().size());
    }

    @Test
    @DisplayName("Should create charging session")
    void shouldCreateNewSession() throws URISyntaxException {
        StartSessionRequest request = new StartSessionRequest();
        when(startSessionService.startSession(request)).thenReturn(new ChargingSession());
        when(locationHeaderBuilder.build(any())).thenReturn(new URI(SOME_URI));

        ResponseEntity<Void> actual = sut.startSession(request);

        assertEquals(HttpStatus.CREATED, actual.getStatusCode());
        List<String> headers = actual.getHeaders().get("Location");
        assertNotNull(headers);
        assertEquals(1, headers.size());
        assertEquals(SOME_URI, headers.get(0));
    }

    @Test
    @DisplayName("Should end charging session")
    void shouldEndSession() {
        EndSessionRequest request = new EndSessionRequest();
        ResponseEntity<Void> actual = sut.endSession(2L, request);

        verify(endSessionService).endSession(2L, request);
        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }
}