package nz.netvalue.domain.service.impl.connector;

import lombok.RequiredArgsConstructor;
import nz.netvalue.domain.service.ChargePointService;
import nz.netvalue.domain.service.connector.CreateConnectorService;
import nz.netvalue.exception.ConnectorAlreadyCreatedException;
import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.model.ChargePoint;
import nz.netvalue.persistence.repository.ChargeConnectorRepository;
import org.springframework.stereotype.Service;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class CreateConnectorServiceImpl implements CreateConnectorService {

    private final ChargePointService chargePointService;
    private final ChargeConnectorRepository repository;

    @Override
    public ChargeConnector addConnectorToPoint(String serialNumber, Long connectorNumber) {
        ChargePoint chargePoint = chargePointService.getChargePoint(serialNumber);
        checkConnectorExists(connectorNumber, chargePoint);
        return createAndSaveConnector(chargePoint, connectorNumber);
    }

    private void checkConnectorExists(Long connectorNumber, ChargePoint chargePoint) {
        Integer count = repository.countByChargePointIdAndConnectorNumber(chargePoint.getId(), connectorNumber);
        if (count > 0) {
            String message = format("Connector with number [%s] and point serial number [%s] already exists",
                    connectorNumber, chargePoint.getSerialNumber());
            throw new ConnectorAlreadyCreatedException(message);
        }
    }

    private ChargeConnector createAndSaveConnector(ChargePoint chargePoint, Long connectorNumber) {
        ChargeConnector connector = createConnector(connectorNumber, chargePoint);
        return repository.save(connector);
    }

    private static ChargeConnector createConnector(Long connectorNumber,
                                                   ChargePoint chargePoint) {
        ChargeConnector connector = new ChargeConnector();
        connector.setChargePoint(chargePoint);
        connector.setConnectorNumber(connectorNumber);
        connector.setMeterValue(0);
        return connector;
    }
}
