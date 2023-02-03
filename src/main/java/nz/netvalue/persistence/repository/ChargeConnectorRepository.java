package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.ChargeConnector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository works with charge connectors
 */
@Repository
public interface ChargeConnectorRepository extends JpaRepository<ChargeConnector, Long> {

    /**
     * Search charge connector by point serial number and connector number
     *
     * @param pointSerialNumber charge point serial number
     * @param connectorNumber   charge connector number
     * @return found charge connector or null
     */
    @Query(value = "select c from ChargeConnector c " +
            "where c.connectorNumber = :connectorNumber " +
            "and c.chargePoint.serialNumber = :pointNumber")
    Optional<ChargeConnector> findByChargePointAndNumber(@Param("pointNumber") String pointSerialNumber,
                                                         @Param("connectorNumber") Long connectorNumber);

    /**
     * Get count charge connectors with number already exists in point
     *
     * @param chargePointId   ID of charge point
     * @param connectorNumber connector number
     * @return count connectors
     */
    Integer countByChargePointIdAndConnectorNumber(Long chargePointId, Long connectorNumber);
}
