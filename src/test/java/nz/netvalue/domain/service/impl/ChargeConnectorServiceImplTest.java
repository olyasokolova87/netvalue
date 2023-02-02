package nz.netvalue.domain.service.impl;

import nz.netvalue.domain.exception.ResourceNotFoundException;
import nz.netvalue.domain.service.ChargePointService;
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

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Test service for charge connectors")
@SpringBootTest(classes = ChargeConnectorServiceImpl.class)
class ChargeConnectorServiceImplTest {

    private static final long CONNECTOR_NUMBER = 1L;
    private static final String SERIAL_NUMBER = "1";
    private static final long ID = 2L;

    @Autowired
    private ChargeConnectorServiceImpl sut;

    @MockBean
    private ChargePointService pointService;

    @MockBean
    private ChargeConnectorRepository connectorRepository;

    @Captor
    private ArgumentCaptor<ChargeConnector> connectorCaptor;

    @Test
    @DisplayName("Should save connector to charge point")
    void shouldSaveConnectorWithCorrectPoint() {
        ChargePoint chargePoint = new ChargePoint();
        chargePoint.setId(ID);
        when(pointService.getChargePoint(SERIAL_NUMBER)).thenReturn(chargePoint);

        sut.addConnectorToPoint(SERIAL_NUMBER, CONNECTOR_NUMBER);

        verify(connectorRepository).save(connectorCaptor.capture());
        assertEquals(chargePoint, connectorCaptor.getValue().getChargePoint());
    }

    @Test
    @DisplayName("Should fails when connector not found")
    void shouldFailsIfConnectorNotFound() {
        when(connectorRepository.findInPointByConnectorNumber(SERIAL_NUMBER, CONNECTOR_NUMBER)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> sut.getConnector(SERIAL_NUMBER, CONNECTOR_NUMBER));
    }

    @Test
    @DisplayName("Should return connector if it exists")
    void shouldGetConnectorByNumber() {
        ChargeConnector expected = new ChargeConnector();
        expected.setConnectorNumber(CONNECTOR_NUMBER);

        when(connectorRepository.findInPointByConnectorNumber(SERIAL_NUMBER, CONNECTOR_NUMBER))
                .thenReturn(Optional.of(expected));

        ChargeConnector actual = sut.getConnector(SERIAL_NUMBER, CONNECTOR_NUMBER);
        assertEquals(CONNECTOR_NUMBER, actual.getConnectorNumber());
    }

    @Test
    @DisplayName("Should save connector with new meter value")
    void shouldSaveConnectorWithNewMeterValue() {
        ChargeConnector connector = new ChargeConnector();
        int expectedMeterValue = 15;
        sut.updateMeterValue(connector, expectedMeterValue);

        verify(connectorRepository).save(connectorCaptor.capture());
        assertEquals(expectedMeterValue, connectorCaptor.getValue().getMeterValue());
    }
}