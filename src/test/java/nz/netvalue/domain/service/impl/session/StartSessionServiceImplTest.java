package nz.netvalue.domain.service.impl.session;

import nz.netvalue.controller.model.StartSessionRequest;
import nz.netvalue.domain.service.RfidTagService;
import nz.netvalue.domain.service.VehicleService;
import nz.netvalue.domain.service.connector.GetConnectorService;
import nz.netvalue.exception.SessionAlreadyStartedException;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = StartSessionServiceImpl.class)
@DisplayName("Test service starts charging session")
class StartSessionServiceImplTest {

    private static final long CONNECTOR_NUMBER = 1L;
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final UUID TAG_NUMBER = UUID.randomUUID();
    private static final String REG_PLATE = "454fg";

    @Autowired
    private StartSessionServiceImpl sut;

    @MockBean
    private ChargingSessionRepository repository;

    @MockBean
    private GetConnectorService getConnectorService;

    @MockBean
    private RfidTagService rfidTagService;

    @MockBean
    private VehicleService vehicleService;

    @Captor
    private ArgumentCaptor<ChargingSession> sessionCaptor;

    @Test
    @DisplayName("Should save session with correct field values")
    void shouldSaveStartedSession() {
        when(getConnectorService.getConnector(any(), any())).thenReturn(createConnector());
        when(rfidTagService.getByUUID(TAG_NUMBER)).thenReturn(createRfIdTag());
        when(vehicleService.getByRegistrationPlate(REG_PLATE)).thenReturn(createVehicle());
        sut.startSession(createStartRequest());

        verify(repository).save(sessionCaptor.capture());
        ChargingSession actual = sessionCaptor.getValue();
        assertEquals(TAG_NUMBER, actual.getRfIdTag().getTagNumber());
        assertEquals(NOW, actual.getStartTime());
        assertEquals(CONNECTOR_NUMBER, actual.getChargeConnector().getConnectorNumber());
        assertEquals(REG_PLATE, actual.getVehicle().getRegistrationPlate());
    }

    @Test
    @DisplayName("Should fail start session if it already started")
    void shouldFailStartSessionIfAlreadyStarted() {
        RfIdTag rfIdTag = createRfIdTag();
        Vehicle vehicle = createVehicle();
        when(rfidTagService.getByUUID(TAG_NUMBER)).thenReturn(rfIdTag);
        when(vehicleService.getByRegistrationPlate(REG_PLATE)).thenReturn(vehicle);
        when(repository.findStartedSession(rfIdTag, vehicle)).thenReturn(Optional.of(createSession()));

        assertThrows(SessionAlreadyStartedException.class, () -> sut.startSession(createStartRequest()));
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
}