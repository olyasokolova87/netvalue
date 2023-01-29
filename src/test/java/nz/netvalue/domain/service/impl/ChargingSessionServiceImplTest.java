package nz.netvalue.domain.service.impl;

import nz.netvalue.controller.dto.StartSessionRequest;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ChargingSessionServiceImpl.class)
@DisplayName("Test service works with charging session")
class ChargingSessionServiceImplTest {

    private static final long CONNECTOR_NUMBER = 1L;
    private static final LocalDateTime NOW = LocalDateTime.now();
    private static final UUID TAG_NUMBER = UUID.randomUUID();

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
    @DisplayName("When save session should filled field correctly")
    void shouldCallSaveSession() {
        when(connectorService.getConnector(any(), any())).thenReturn(createConnector());
        when(rfidTagService.getByUUID(TAG_NUMBER)).thenReturn(createRfIdTag());
        sut.createSession(createStartRequest());

        verify(repository).save(sessionCaptor.capture());
        assertEquals(TAG_NUMBER, sessionCaptor.getValue().getRfIdTag().getTagNumber());
        assertEquals(NOW, sessionCaptor.getValue().getStartTime());
        assertEquals(CONNECTOR_NUMBER, sessionCaptor.getValue().getChargeConnector().getConnectorNumber());
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
}