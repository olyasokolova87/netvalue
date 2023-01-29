package nz.netvalue.domain.service;

import nz.netvalue.persistence.model.ChargingSession;

import java.time.LocalDate;
import java.util.List;

/**
 * Service works with charging session
 */
public interface ChargingSessionService {

    /**
     * Get list of all charging session. Can be filtered by period
     *
     * @param dateFrom start period
     * @param dateTo   end period
     * @return list of charging session
     */
    List<ChargingSession> getChargeSessions(LocalDate dateFrom, LocalDate dateTo);
}