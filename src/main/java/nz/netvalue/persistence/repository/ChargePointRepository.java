package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.ChargePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for charge connector points
 */
@Repository
public interface ChargePointRepository extends JpaRepository<ChargePoint, Long> {

    Optional<ChargePoint> findBySerialNumber(String serialNumber);
}
