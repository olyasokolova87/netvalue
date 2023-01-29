package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.RfIdTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

/**
 * Repository works with RFID tags
 */
@Repository
public interface RfidTagRepository extends JpaRepository<RfIdTag, Long> {

    /**
     * Search RFID tag by tag number
     *
     * @param tagNumber RFID tag number
     * @return Found RFID tag or null
     */
    Optional<RfIdTag> findByTagNumber(UUID tagNumber);
}
