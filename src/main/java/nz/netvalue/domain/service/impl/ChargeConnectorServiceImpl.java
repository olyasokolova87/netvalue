package nz.netvalue.domain.service.impl;

import nz.netvalue.domain.exception.ResourceNotFoundException;
import nz.netvalue.domain.service.ChargeConnectorService;
import nz.netvalue.domain.service.ChargePointService;
import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.model.ChargePoint;
import nz.netvalue.persistence.repository.ChargeConnectorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
public class ChargeConnectorServiceImpl implements ChargeConnectorService {

    private final ChargePointService chargePointService;
    private final ChargeConnectorRepository connectorRepository;

    public ChargeConnectorServiceImpl(ChargeConnectorRepository connectorRepository,
                                      ChargePointService chargePointService) {
        this.connectorRepository = connectorRepository;
        this.chargePointService = chargePointService;
    }

    @Override
    public ChargeConnector addConnectorToPoint(String serialNumber, Long connectorNumber) {
        ChargePoint chargePoint = chargePointService.getChargePoint(serialNumber);

        ChargeConnector connector = createConnector(connectorNumber, chargePoint);
        return connectorRepository.save(connector);
    }

    @Override
    public ChargeConnector getConnector(String pointSerialNumber, Long connectorNumber) {
        Optional<ChargeConnector> optional =
                connectorRepository.findInPointByConnectorNumber(pointSerialNumber, connectorNumber);
        if (optional.isEmpty()) {
            String message = format("Charge connector with number = [%s] not exists", connectorNumber);
            throw new ResourceNotFoundException(message);
        }
        return optional.get();
    }

    @Override
    public void updateMeterValue(ChargeConnector connector, Integer meterValue) {
        connector.setMeterValue(meterValue);
        connectorRepository.save(connector);
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
