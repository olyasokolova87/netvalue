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
    @Query(value = "select * from charge_points CP_ join charge_connectors CC_ on CP_.id = CC_.charge_point_id " +
            "where CC_.connector_number = :connectorNumber and CP_.serial_number = :pointNumber",
            nativeQuery = true)
    Optional<ChargeConnector> findInPointByConnectorNumber(@Param("pointNumber") String pointSerialNumber,
                                                           @Param("connectorNumber") Long connectorNumber);
}
