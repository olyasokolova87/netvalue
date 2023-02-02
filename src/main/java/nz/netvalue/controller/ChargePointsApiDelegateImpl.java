package nz.netvalue.controller;

import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.dto.ConnectorRequest;
import nz.netvalue.controller.utils.LocationBuilder;
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
    private final LocationBuilder locationBuilder;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> addConnector(String serialNumber, ConnectorRequest connectorRequest) {
        ChargeConnector created = chargeConnectorService.addConnectorToPoint(serialNumber,
                connectorRequest.getConnectorNumber());
        URI location = locationBuilder.build(created.getId());

        return ResponseEntity.created(location).build();
    }
}
