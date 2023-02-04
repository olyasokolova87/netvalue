package nz.netvalue.domain.service.session;

import nz.netvalue.persistence.model.ChargingSession;

import java.time.LocalDate;
import java.util.List;

/**
 * Service gets charging sessions
 */
public interface GetSessionService {

    /**
     * Get list of all charging session. Can be filtered by period
     *
     * @param dateFrom start period
     * @param dateTo   end period
     * @return list of charging session
     */
    List<ChargingSession> getChargeSessions(LocalDate dateFrom, LocalDate dateTo);
}
