package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.ChargingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * Repository works with charging sessions
 */
@Repository
public interface ChargingSessionRepository extends JpaRepository<ChargingSession, Long> {

    /**
     * Search charging sessions in period
     *
     * @param startDate start period date
     * @param endDate   end period date
     * @return list of charging sessions
     */
    @Query("select c from ChargingSession c " +
            "where (:startDate is not null or c.startTime >= :startDate) " +
            "and (:endDate is not null or c.endTime <= :endDate)")
    List<ChargingSession> findByDatePeriod(@Param("startDate") LocalDate startDate,
                                           @Param("endDate") LocalDate endDate);
}
