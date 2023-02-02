package nz.netvalue.controller;

import nz.netvalue.controller.dto.ChargingSessionResponse;
import nz.netvalue.controller.dto.EndSessionRequest;
import nz.netvalue.controller.dto.StartSessionRequest;
import nz.netvalue.controller.mapper.ChargingSessionMapper;
import nz.netvalue.controller.utils.LocationBuilder;
import nz.netvalue.domain.service.ChargingSessionService;
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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@DisplayName("Test delegate for session API")
@SpringBootTest(classes = ChargingSessionApiDelegateImpl.class)
class ChargingSessionApiDelegateImplTest {

    private static final String SOME_URI = "some/uri";

    @Autowired
    private ChargingSessionApiDelegateImpl sut;

    @MockBean
    private ChargingSessionService service;

    @MockBean
    private ChargingSessionMapper mapper;

    @MockBean
    private LocationBuilder locationBuilder;

    @Test
    @DisplayName("Should return list of charging sessions")
    void shouldReturnSessionListResponse() {
        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();
        when(service.getChargeSessions(dateFrom, dateTo))
                .thenReturn(Collections.singletonList(new ChargingSession()));
        when(mapper.toResponseList(anyList()))
                .thenReturn(Collections.singletonList(new ChargingSessionResponse()));

        ResponseEntity<List<ChargingSessionResponse>> actual = sut.getChargeSessions(dateFrom, dateTo);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals(1, actual.getBody().size());
    }

    @Test
    @DisplayName("Should create charging session")
    void shouldCreateNewSession() throws URISyntaxException {
        StartSessionRequest request = new StartSessionRequest();
        when(service.startSession(request)).thenReturn(new ChargingSession());
        when(locationBuilder.build(any())).thenReturn(new URI(SOME_URI));

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
        ResponseEntity<Void> actual = sut.endSession(request);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
    }
}