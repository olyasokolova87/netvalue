package nz.netvalue.domain.service.connector.impl;

import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.model.ChargePoint;
import nz.netvalue.persistence.repository.ChargeConnectorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("Test service for get charge connectors")
@SpringBootTest(classes = GetConnectorServiceImpl.class)
class GetConnectorServiceImplTest {

    private static final long CONNECTOR_NUMBER = 1L;
    private static final String SERIAL_NUMBER = "1";
    private static final long ID = 2L;

    @Autowired
    private GetConnectorServiceImpl sut;

    @MockBean
    private ChargeConnectorRepository connectorRepository;

    @Test
    @DisplayName("Should fails when connector not found")
    void shouldFailsWhenConnectorNotFound() {
        when(connectorRepository.findByChargePointAndNumber(SERIAL_NUMBER, CONNECTOR_NUMBER)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> sut.getConnector(SERIAL_NUMBER, CONNECTOR_NUMBER));
    }

    @Test
    @DisplayName("Should return connector if it exists")
    void shouldGetConnectorByNumber() {
        ChargeConnector expected = new ChargeConnector();
        expected.setConnectorNumber(CONNECTOR_NUMBER);

        when(connectorRepository.findByChargePointAndNumber(SERIAL_NUMBER, CONNECTOR_NUMBER))
                .thenReturn(Optional.of(expected));

        ChargeConnector actual = sut.getConnector(SERIAL_NUMBER, CONNECTOR_NUMBER);
        assertEquals(CONNECTOR_NUMBER, actual.getConnectorNumber());
    }

    private static ChargePoint createPoint() {
        ChargePoint chargePoint = new ChargePoint();
        chargePoint.setId(ID);
        return chargePoint;
    }
}