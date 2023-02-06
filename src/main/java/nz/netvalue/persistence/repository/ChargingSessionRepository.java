package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.ChargingSession;
import nz.netvalue.persistence.model.RfIdTag;
import nz.netvalue.persistence.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

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
     * @return list of charging sessions order by createdDate desc
     */
    @Query("select c from ChargingSession c " +
            "where :startPeriod is null or " +
            "(c.startTime between :startPeriod and :endPeriod " +
            "and (c.endTime is null or c.endTime between :startPeriod and :endPeriod)) " +
            "order by c.createdDate desc")
    List<ChargingSession> findByDatePeriod(@Param("startPeriod") LocalDateTime startPeriod,
                                           @Param("endPeriod") LocalDateTime endPeriod);

    /**
     * Search started charging session for RFID tag and vehicle
     *
     * @param rfIdTag RFID tag
     * @param vehicle vehicle
     * @return true, if vehicle have not ended session RFID tag
     */
    @Query("select c from ChargingSession c " +
            "where c.rfIdTag = :rfIdTag and c.vehicle = :vehicle " +
            "and c.endTime is null and c.errorMessage is null")
    Optional<ChargingSession> findStartedSession(@Param("rfIdTag") RfIdTag rfIdTag,
                                                 @Param("vehicle") Vehicle vehicle);
}
