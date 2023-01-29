package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.ChargingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository wotk with charging sessions
 */
@Repository
public interface ChargingSessionRepository extends JpaRepository<ChargingSession, Long> {

    @Query("select c from ChargingSession c " +
            "where (?1 is not null or c.startTime >= ?1) " +
            "and (?2 is not null or c.endTime <= ?2)")
    List<ChargingSession> findByDatePeriod(LocalDate startTime, LocalDate endTime);
}
