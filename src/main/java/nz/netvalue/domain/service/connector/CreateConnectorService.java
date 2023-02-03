package nz.netvalue.domain.service.connector;

import nz.netvalue.persistence.model.ChargeConnector;

/**
 * Service creates charge connectors
 */
public interface CreateConnectorService {

    /**
     * Add connection to charge point
     *
     * @param serialNumber    charge point's serial number
     * @param connectorNumber number of charge connector
     * @return created connector
     */
    ChargeConnector addConnectorToPoint(String serialNumber, Long connectorNumber);
}
