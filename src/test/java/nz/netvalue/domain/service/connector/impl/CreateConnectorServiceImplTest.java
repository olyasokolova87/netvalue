package nz.netvalue.domain.service.connector.impl;

import nz.netvalue.domain.service.point.ChargePointService;
import nz.netvalue.exception.ConnectorAlreadyCreatedException;
import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.model.ChargePoint;
import nz.netvalue.persistence.repository.ChargeConnectorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Test service for create charge connectors")
@SpringBootTest(classes = CreateConnectorServiceImpl.class)
class CreateConnectorServiceImplTest {

    private static final long CONNECTOR_NUMBER = 1L;
    private static final String SERIAL_NUMBER = "1";
    private static final long ID = 2L;

    @Autowired
    private CreateConnectorServiceImpl sut;

    @MockBean
    private ChargePointService pointService;

    @MockBean
    private ChargeConnectorRepository connectorRepository;

    @Captor
    private ArgumentCaptor<ChargeConnector> connectorCaptor;

    @Test
    @DisplayName("Should save connector to charge point")
    void shouldSaveConnectorWithCorrectPoint() {
        ChargePoint chargePoint = createPoint();
        when(pointService.getChargePoint(SERIAL_NUMBER)).thenReturn(chargePoint);

        sut.addConnectorToPoint(SERIAL_NUMBER, CONNECTOR_NUMBER);

        verify(connectorRepository).save(connectorCaptor.capture());
        assertEquals(chargePoint, connectorCaptor.getValue().getChargePoint());
    }

    @Test
    @DisplayName("Should fails when connector exists")
    void shouldFailsWhenConnectorExists() {
        ChargePoint chargePoint = createPoint();
        when(pointService.getChargePoint(SERIAL_NUMBER)).thenReturn(chargePoint);
        when(connectorRepository.countByChargePointIdAndConnectorNumber(any(), any()))
                .thenReturn(1);

        assertThrows(ConnectorAlreadyCreatedException.class,
                () -> sut.addConnectorToPoint(SERIAL_NUMBER, CONNECTOR_NUMBER));
    }

    private static ChargePoint createPoint() {
        ChargePoint chargePoint = new ChargePoint();
        chargePoint.setId(ID);
        return chargePoint;
    }
}