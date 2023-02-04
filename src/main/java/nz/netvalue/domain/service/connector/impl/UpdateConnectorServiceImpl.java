package nz.netvalue.domain.service.connector.impl;

import lombok.RequiredArgsConstructor;
import nz.netvalue.domain.service.connector.UpdateConnectorService;
import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.repository.ChargeConnectorRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UpdateConnectorServiceImpl implements UpdateConnectorService {

    private final ChargeConnectorRepository repository;

    @Override
    public void updateMeterValue(ChargeConnector connector, Integer meterValue) {
        connector.setMeterValue(meterValue);
        repository.save(connector);
    }
}
