package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.ChargingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository works with charging sessions
 */
@Repository
public interface ChargingSessionRepository extends JpaRepository<ChargingSession, Long> {

    /**
     * Search charging sessions in period
     *
     * @param startPeriod start period date
     * @param endPeriod   end period date
     * @return list of charging sessions
     */
    @Query("select c from ChargingSession c " +
            "where :startPeriod is null or " +
            "(c.startTime between :startPeriod and :endPeriod " +
            "and (c.endTime is null or c.endTime between :startPeriod and :endPeriod))")
    List<ChargingSession> findByDatePeriod(@Param("startPeriod") LocalDateTime startPeriod,
                                           @Param("endPeriod") LocalDateTime endPeriod);
}
