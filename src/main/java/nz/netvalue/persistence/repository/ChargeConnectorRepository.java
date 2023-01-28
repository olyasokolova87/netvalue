package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.ChargeConnector;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for charge connector entities
 */
@Repository
public interface ChargeConnectorRepository extends JpaRepository<ChargeConnector, Long> {

}
