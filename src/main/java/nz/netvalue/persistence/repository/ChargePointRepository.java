package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.ChargePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository works with charge points
 */
@Repository
public interface ChargePointRepository extends JpaRepository<ChargePoint, Long> {

    /**
     * Search charge point by serial number
     *
     * @param serialNumber charge point serial number
     * @return found charge point or null
     */
    Optional<ChargePoint> findBySerialNumber(String serialNumber);
}
