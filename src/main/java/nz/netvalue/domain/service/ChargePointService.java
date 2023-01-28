package nz.netvalue.domain.service;

import nz.netvalue.persistence.model.ChargePoint;

/**
 * Service works with charge points
 */
public interface ChargePointService {

    /**
     * Get charge point by serial number
     *
     * @param serialNumber serial number
     * @return charge point
     * can throw ResourceNotFoundException if charge point not exists
     */
    ChargePoint getChargePoint(String serialNumber);
}
