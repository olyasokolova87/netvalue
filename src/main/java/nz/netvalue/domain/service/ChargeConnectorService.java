package nz.netvalue.domain.service;

import nz.netvalue.persistence.model.ChargeConnector;

/**
 * Service works with charge connectors
 */
public interface ChargeConnectorService {

    /**
     * Add connection to charge point
     *
     * @param serialNumber    charge point's serial number
     * @param connectorNumber number of charge connector
     * @return created connector
     */
    ChargeConnector addConnectorToPoint(String serialNumber, Long connectorNumber);

    /**
     * Get charge connector by connectorNumber
     *
     * @param pointSerialNumber charge point serial number
     * @param connectorNumber   charge connector number
     * @return connector
     * Can throw {@link nz.netvalue.domain.exception.ResourceNotFoundException} if charge connector not exists
     */
    ChargeConnector getConnector(String pointSerialNumber, Long connectorNumber);

    /**
     * Update meter value in charge connector
     *
     * @param connector  charge connector
     * @param meterValue new value of meter
     */
    void updateMeterValue(ChargeConnector connector, Integer meterValue);
}
