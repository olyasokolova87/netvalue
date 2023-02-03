package nz.netvalue.domain.service.impl.connector;

import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.repository.ChargeConnectorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;

@DisplayName("Test service for charge connectors")
@SpringBootTest(classes = UpdateConnectorServiceImpl.class)
class UpdateConnectorServiceImplTest {

    @Autowired
    private UpdateConnectorServiceImpl sut;

    @MockBean
    private ChargeConnectorRepository connectorRepository;

    @Captor
    private ArgumentCaptor<ChargeConnector> connectorCaptor;

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