package nz.netvalue.domain.service.vehicle;

import nz.netvalue.exception.ResourceNotFoundException;
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
     * If vehicle not exists, throws {@link ResourceNotFoundException}
     */
    Vehicle getByRegistrationPlate(String regPlate);
}
