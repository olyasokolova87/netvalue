package nz.netvalue.controller.impl;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.ChargePointsApiDelegate;
import nz.netvalue.controller.model.ConnectorRequest;
import nz.netvalue.controller.utils.LocationHeaderBuilder;
import nz.netvalue.domain.service.connector.CreateConnectorService;
import nz.netvalue.persistence.model.ChargeConnector;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class ChargePointsApiDelegateImpl implements ChargePointsApiDelegate {

    private final CreateConnectorService connectorService;
    private final LocationHeaderBuilder locationHeaderBuilder;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Timed(value = "add.connector")
    public ResponseEntity<Void> addConnector(String serialNumber, ConnectorRequest connectorRequest) {
        ChargeConnector created = connectorService.addConnectorToPoint(serialNumber,
                connectorRequest.getConnectorNumber());
        URI location = locationHeaderBuilder.build(created.getId());

        return ResponseEntity.created(location).build();
    }
}
