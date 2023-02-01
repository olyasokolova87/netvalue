package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository works with vehicles
 */
@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    /**
     * Search vehicle by registration plate
     *
     * @param regPlate registration plate
     * @return Found vehicle or null
     */
    Optional<Vehicle> findByRegistrationPlate(String regPlate);
}
