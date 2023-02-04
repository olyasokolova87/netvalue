package nz.netvalue.domain.service.impl.session;

import nz.netvalue.controller.model.EndSessionRequest;
import nz.netvalue.controller.model.StartSessionRequest;
import nz.netvalue.domain.service.connector.UpdateConnectorService;
import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.model.ChargingSession;
import nz.netvalue.persistence.model.RfIdTag;
import nz.netvalue.persistence.model.Vehicle;
import nz.netvalue.persistence.repository.ChargingSessionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = EndSessionServiceImpl.class)
@DisplayName("Test service for charging session")
class EndSessionServiceImplTest {

    private static final long CONNECTOR_NUMBER = 1L;
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final UUID TAG_NUMBER = UUID.randomUUID();
    private static final String REG_PLATE = "454fg";
    private static final long SESSION_ID = 2L;
    private static final int METER_VALUE = 15;

    @Autowired
    private EndSessionServiceImpl sut;

    @MockBean
    private ChargingSessionRepository repository;

    @MockBean
    private UpdateConnectorService updateConnectorService;

    @Captor
    private ArgumentCaptor<ChargeConnector> connectorCaptor;

    @Test
    @DisplayName("Should update session and connector meter value")
    void shouldUpdateSessionAndConnectorMeter() {
        ChargingSession session = createSession();
        when(repository.findById(SESSION_ID)).thenReturn(Optional.of(session));
        sut.endSession(createEndRequest());

        verify(repository).findById(SESSION_ID);
        verify(repository).save(session);
        verify(updateConnectorService).updateMeterValue(connectorCaptor.capture(), eq(METER_VALUE));
        assertEquals(CONNECTOR_NUMBER, connectorCaptor.getValue().getConnectorNumber());
    }

    @Test
    @DisplayName("Should update only session if connector meter value is null")
    void shouldUpdateOnlySessionWhenConnectorMeterIsNull() {
        ChargingSession session = createSession();
        when(repository.findById(SESSION_ID)).thenReturn(Optional.of(session));
        EndSessionRequest endRequest = createEndRequest();
        endRequest.setMeterValue(null);
        sut.endSession(endRequest);

        verify(repository).findById(SESSION_ID);
        verify(repository).save(session);
        verifyNoInteractions(updateConnectorService);
    }

    @Test
    @DisplayName("Should fail end session if it doesn't exist")
    void shouldFailEndSession() {
        when(repository.findById(SESSION_ID)).thenReturn(Optional.empty());
        assertThrows(ResourceNotFoundException.class,
                () -> sut.endSession(createEndRequest()));
    }

    @Test
    @DisplayName("Shouldn't update session when already ended")
    void shouldNotUpdateSessionIfAlreadyEnded() {
        ChargingSession session = new ChargingSession();
        session.setEndTime(NOW);

        EndSessionRequest endRequest = createEndRequest();
        endRequest.setEndTime(NOW);
        when(repository.findById(SESSION_ID)).thenReturn(Optional.of(session));

        sut.endSession(endRequest);

        verify(repository).findById(SESSION_ID);
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(updateConnectorService);
    }

    private static RfIdTag createRfIdTag() {
        RfIdTag rfIdTag = new RfIdTag();
        rfIdTag.setTagNumber(TAG_NUMBER);
        return rfIdTag;
    }

    private static Vehicle createVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationPlate(REG_PLATE);
        return vehicle;
    }

    private static ChargingSession createSession() {
        ChargingSession session = new ChargingSession();
        session.setChargeConnector(createConnector());
        return session;
    }

    private static ChargeConnector createConnector() {
        ChargeConnector connector = new ChargeConnector();
        connector.setConnectorNumber(CONNECTOR_NUMBER);
        return connector;
    }

    private static StartSessionRequest createStartRequest() {
        StartSessionRequest request = new StartSessionRequest();
        request.setConnectorNumber(CONNECTOR_NUMBER);
        request.setStartTime(NOW);
        request.setRfIdTagNumber(TAG_NUMBER.toString());
        request.setVehicleRegistrationPlate(REG_PLATE);
        return request;
    }

    private static EndSessionRequest createEndRequest() {
        EndSessionRequest request = new EndSessionRequest();
        request.setSessionId(SESSION_ID);
        request.setEndTime(NOW.minusMinutes(1));
        request.setMeterValue(METER_VALUE);
        return request;
    }
}