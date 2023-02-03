package nz.netvalue.domain.service.impl.connector;

import lombok.RequiredArgsConstructor;
import nz.netvalue.domain.service.connector.GetConnectorService;
import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.repository.ChargeConnectorRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class GetConnectorServiceImpl implements GetConnectorService {

    private final ChargeConnectorRepository repository;

    @Override
    public ChargeConnector getConnector(String pointSerialNumber, Long connectorNumber) {
        Optional<ChargeConnector> optional =
                repository.findByChargePointAndNumber(pointSerialNumber, connectorNumber);
        if (optional.isEmpty()) {
            String message = format("Charge connector with number = [%s] not exists", connectorNumber);
            throw new ResourceNotFoundException(message);
        }
        return optional.get();
    }
}
