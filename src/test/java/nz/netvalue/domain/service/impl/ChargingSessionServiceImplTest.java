package nz.netvalue.domain.service.impl;

import nz.netvalue.controller.dto.EndSessionRequest;
import nz.netvalue.controller.dto.StartSessionRequest;
import nz.netvalue.domain.exception.ResourceNotFoundException;
import nz.netvalue.domain.service.ChargeConnectorService;
import nz.netvalue.domain.service.RfidTagService;
import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.model.ChargingSession;
import nz.netvalue.persistence.model.RfIdTag;
import nz.netvalue.persistence.repository.ChargingSessionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = ChargingSessionServiceImpl.class)
@DisplayName("Test service works with charging session")
class ChargingSessionServiceImplTest {

    private static final long CONNECTOR_NUMBER = 1L;
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final UUID TAG_NUMBER = UUID.randomUUID();
    private static final long SESSION_ID = 2L;
    private static final int METER_VALUE = 15;

    @Autowired
    private ChargingSessionServiceImpl sut;

    @MockBean
    private ChargingSessionRepository repository;

    @MockBean
    private ChargeConnectorService connectorService;

    @MockBean
    private RfidTagService rfidTagService;

    @Captor
    private ArgumentCaptor<ChargingSession> sessionCaptor;

    @Captor
    private ArgumentCaptor<ChargeConnector> connectorCaptor;

    @Test
    @DisplayName("Call find all sessions method if date period not filled")
    void shouldCallFindAll() {
        sut.getChargeSessions(null, null);

        verify(repository).findAll();
    }

    @Test
    @DisplayName("Call find by date period method, if date period filled")
    void shouldCallFindByDatePeriod() {
        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();
        sut.getChargeSessions(dateFrom, dateTo);

        verify(repository).findByDatePeriod(dateFrom, dateTo);
    }

    @Test
    @DisplayName("When create session should save with filled field correctly")
    void shouldCreateNewSession() {
        when(connectorService.getConnector(any(), any())).thenReturn(createConnector());
        when(rfidTagService.getByUUID(TAG_NUMBER)).thenReturn(createRfIdTag());
        sut.startSession(createStartRequest());

        verify(repository).save(sessionCaptor.capture());
        assertEquals(TAG_NUMBER, sessionCaptor.getValue().getRfIdTag().getTagNumber());
        assertEquals(NOW, sessionCaptor.getValue().getStartTime());
        assertEquals(CONNECTOR_NUMBER, sessionCaptor.getValue().getChargeConnector().getConnectorNumber());
    }

    @Test
    @DisplayName("Should call save session and update connector meter value")
    void shouldUpdateSession() {
        ChargingSession session = new ChargingSession();
        session.setChargeConnector(createConnector());
        when(repository.findById(SESSION_ID)).thenReturn(Optional.of(session));
        sut.endSession(createEndRequest());

        verify(repository).save(session);
        verify(connectorService).updateMeterValue(connectorCaptor.capture(), eq(METER_VALUE));
        assertEquals(CONNECTOR_NUMBER, connectorCaptor.getValue().getConnectorNumber());
    }

    @Test
    @DisplayName("Throws if session not exists")
    void shouldThrowsSessionNotExists() {
        when(repository.findById(SESSION_ID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> sut.endSession(createEndRequest()));
    }

    @Test
    @DisplayName("If session with this end time already ended, nothing have to save")
    void shouldNotSaveIfAlreadySavedWithEqualEndTime() {
        ChargingSession session = new ChargingSession();
        session.setEndTime(NOW);

        EndSessionRequest endRequest = createEndRequest();
        endRequest.setDateTime(NOW);
        when(repository.findById(SESSION_ID)).thenReturn(Optional.of(session));

        sut.endSession(endRequest);

        verify(repository).findById(SESSION_ID);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(connectorService);
    }

    private static RfIdTag createRfIdTag() {
        RfIdTag rfIdTag = new RfIdTag();
        rfIdTag.setTagNumber(TAG_NUMBER);
        return rfIdTag;
    }

    private static ChargeConnector createConnector() {
        ChargeConnector connector = new ChargeConnector();
        connector.setConnectorNumber(CONNECTOR_NUMBER);
        return connector;
    }

    private static StartSessionRequest createStartRequest() {
        StartSessionRequest request = new StartSessionRequest();
        request.setConnectorNumber(CONNECTOR_NUMBER);
        request.setDateTime(NOW);
        request.setRfIdTagNumber(TAG_NUMBER.toString());
        return request;
    }

    private static EndSessionRequest createEndRequest() {
        EndSessionRequest request = new EndSessionRequest();
        request.setSessionId(SESSION_ID);
        request.setDateTime(NOW.minusMinutes(1));
        request.setMeterValue(METER_VALUE);
        return request;
    }
}