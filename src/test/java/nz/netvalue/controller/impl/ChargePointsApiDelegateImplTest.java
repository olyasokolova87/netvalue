package nz.netvalue.controller.impl;

import nz.netvalue.controller.model.ConnectorRequest;
import nz.netvalue.controller.utils.LocationHeaderBuilder;
import nz.netvalue.domain.service.ChargeConnectorService;
import nz.netvalue.persistence.model.ChargeConnector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Test delegate for charge point API")
@SpringBootTest(classes = ChargePointsApiDelegateImpl.class)
class ChargePointsApiDelegateImplTest {

    private static final long CONNECTOR_NUMBER = 1L;
    private static final String SERIAL_NUMBER = "1";
    private static final long ID = 2L;
    private static final String SOME_URI = "some/uri";

    @Autowired
    private ChargePointsApiDelegateImpl sut;

    @MockBean
    private ChargeConnectorService chargeConnectorService;

    @MockBean
    private LocationHeaderBuilder locationHeaderBuilder;

    @Test
    @DisplayName("Should call correctly")
    void shouldCallCorrectly() throws URISyntaxException {
        when(chargeConnectorService.addConnectorToPoint(SERIAL_NUMBER, CONNECTOR_NUMBER))
                .thenReturn(createConnector());
        when(locationHeaderBuilder.build(ID)).thenReturn(new URI(SOME_URI));

        sut.addConnector(SERIAL_NUMBER, createRequest());

        verify(chargeConnectorService).addConnectorToPoint(SERIAL_NUMBER, CONNECTOR_NUMBER);
        verify(locationHeaderBuilder).build(ID);
    }

    @Test
    @DisplayName("Should get response with location header")
    void shouldGetResponseWitLocationHeader() throws URISyntaxException {
        when(chargeConnectorService.addConnectorToPoint(SERIAL_NUMBER, CONNECTOR_NUMBER))
                .thenReturn(createConnector());
        when(locationHeaderBuilder.build(ID)).thenReturn(new URI(SOME_URI));

        ResponseEntity<Void> actualResponse = sut.addConnector(SERIAL_NUMBER, createRequest());

        assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
        List<String> headers = actualResponse.getHeaders().get("Location");
        assertNotNull(headers);
        assertEquals(1, headers.size());
        assertEquals(SOME_URI, headers.get(0));
    }

    private static ChargeConnector createConnector() {
        ChargeConnector connector = new ChargeConnector();
        connector.setId(ID);
        return connector;
    }

    private static ConnectorRequest createRequest() {
        ConnectorRequest connectorRequest = new ConnectorRequest();
        connectorRequest.setConnectorNumber(CONNECTOR_NUMBER);
        return connectorRequest;
    }
}