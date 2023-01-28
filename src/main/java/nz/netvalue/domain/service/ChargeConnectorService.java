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
}
