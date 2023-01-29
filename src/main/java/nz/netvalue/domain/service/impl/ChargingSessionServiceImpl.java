package nz.netvalue.domain.service.impl;

import nz.netvalue.domain.service.ChargingSessionService;
import nz.netvalue.persistence.model.ChargingSession;
import nz.netvalue.persistence.repository.ChargingSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ChargingSessionServiceImpl implements ChargingSessionService {

    private final ChargingSessionRepository repository;

    public ChargingSessionServiceImpl(ChargingSessionRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ChargingSession> getChargeSessions(LocalDate dateFrom,
                                                   LocalDate dateTo) {
        if (dateFrom == null && dateTo == null) {
            return repository.findAll();
        } else {
            return repository.findByDatePeriod(dateFrom, dateTo);
        }
    }
}
