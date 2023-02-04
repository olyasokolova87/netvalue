package nz.netvalue.domain.service.impl.session;

import lombok.RequiredArgsConstructor;
import nz.netvalue.domain.service.session.GetSessionService;
import nz.netvalue.persistence.model.ChargingSession;
import nz.netvalue.persistence.repository.ChargingSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetSessionServiceImpl implements GetSessionService {

    private final ChargingSessionRepository repository;

    @Override
    public List<ChargingSession> getChargeSessions(LocalDate dateFrom, LocalDate dateTo) {
        LocalDateTime startPeriod = dateFrom != null ? dateFrom.atStartOfDay() : null;
        LocalDate endDate = dateTo != null ? dateTo : LocalDate.now();
        return repository.findByDatePeriod(startPeriod, endDate.atTime(LocalTime.MAX));
    }
}
