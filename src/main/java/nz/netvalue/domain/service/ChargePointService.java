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
     * If charge point not exists, throws {@link nz.netvalue.domain.exception.ResourceNotFoundException}
     */
    ChargePoint getChargePoint(String serialNumber);
}
