package nz.netvalue.domain.service.session.impl;

import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.model.EndSessionRequest;
import nz.netvalue.domain.service.connector.UpdateConnectorService;
import nz.netvalue.domain.service.session.EndSessionService;
import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.ChargingSession;
import nz.netvalue.persistence.repository.ChargingSessionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class EndSessionServiceImpl implements EndSessionService {

    private final ChargingSessionRepository repository;
    private final UpdateConnectorService updateConnectorService;

    @Transactional
    @Override
    public void endSession(Long sessionId, EndSessionRequest request) {
        ChargingSession session = getSessionById(sessionId);
        if (session.getEndTime() != null && session.getEndTime().equals(request.getEndTime())) {
            //already finished, just return
            return;
        }

        session.setEndTime(request.getEndTime() == null ? LocalDateTime.now() : request.getEndTime());
        session.setErrorMessage(request.getErrorMessage());
        repository.save(session);

        if (request.getMeterValue() != null) {
            updateConnectorService.updateMeterValue(session.getChargeConnector(), request.getMeterValue());
        }
    }

    private ChargingSession getSessionById(Long sessionId) {
        Optional<ChargingSession> optional = repository.findById(sessionId);
        if (optional.isEmpty()) {
            String message = format("Charging session with ID = [%s] not exists", sessionId);
            throw new ResourceNotFoundException(message);
        }
        return optional.get();
    }
}
