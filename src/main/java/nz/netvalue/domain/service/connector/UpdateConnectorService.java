package nz.netvalue.domain.service.connector;

import nz.netvalue.persistence.model.ChargeConnector;

/**
 * Service updates charge connectors
 */
public interface UpdateConnectorService {

    /**
     * Update meter value in charge connector
     *
     * @param connector  charge connector
     * @param meterValue new value of meter
     */
    void updateMeterValue(ChargeConnector connector, Integer meterValue);
}
