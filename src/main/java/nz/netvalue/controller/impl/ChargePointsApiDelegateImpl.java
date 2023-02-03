package nz.netvalue.controller.impl;

import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.ChargePointsApiDelegate;
import nz.netvalue.controller.model.ConnectorRequest;
import nz.netvalue.controller.utils.LocationHeaderBuilder;
import nz.netvalue.domain.service.ChargeConnectorService;
import nz.netvalue.persistence.model.ChargeConnector;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class ChargePointsApiDelegateImpl implements ChargePointsApiDelegate {

    private final ChargeConnectorService chargeConnectorService;
    private final LocationHeaderBuilder locationHeaderBuilder;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addConnector(String serialNumber, ConnectorRequest connectorRequest) {
        ChargeConnector created = chargeConnectorService.addConnectorToPoint(serialNumber,
                connectorRequest.getConnectorNumber());
        URI location = locationHeaderBuilder.build(created.getId());

        return ResponseEntity.created(location).build();
    }
}
