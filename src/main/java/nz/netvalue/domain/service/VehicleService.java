package nz.netvalue.domain.service;

import nz.netvalue.persistence.model.Vehicle;

/**
 * Service works with vehicles
 */
public interface VehicleService {

    /**
     * Get vehicle by registration plate
     *
     * @param regPlate registration plate
     * @return Vehicle
     * If vehicle not exists, throws {@link nz.netvalue.domain.exception.ResourceNotFoundException}
     */
    Vehicle getByRegistrationPlate(String regPlate);
}
