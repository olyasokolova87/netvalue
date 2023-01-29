package nz.netvalue.domain.service.impl;

import nz.netvalue.controller.dto.EndSessionRequest;
import nz.netvalue.controller.dto.StartSessionRequest;
import nz.netvalue.domain.exception.ResourceNotFoundException;
import nz.netvalue.domain.service.ChargeConnectorService;
import nz.netvalue.domain.service.ChargingSessionService;
import nz.netvalue.domain.service.RfidTagService;
import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.model.ChargingSession;
import nz.netvalue.persistence.model.RfIdTag;
import nz.netvalue.persistence.repository.ChargingSessionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@Service
public class ChargingSessionServiceImpl implements ChargingSessionService {

    private final ChargingSessionRepository repository;
    private final ChargeConnectorService connectorService;
    private final RfidTagService rfidTagService;

    public ChargingSessionServiceImpl(ChargingSessionRepository repository,
                                      ChargeConnectorService connectorService,
                                      RfidTagService rfidTagService) {
        this.repository = repository;
        this.connectorService = connectorService;
        this.rfidTagService = rfidTagService;
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

    @Override
    public ChargingSession startSession(StartSessionRequest request) {
        ChargeConnector connector = connectorService.getConnector(
                request.getPointSerialNumber(),
                request.getConnectorNumber());
        RfIdTag rfIdTag = rfidTagService.getByUUID(UUID.fromString(request.getRfIdTagNumber()));

        ChargingSession session = new ChargingSession();
        session.setChargeConnector(connector);
        session.setStartTime(request.getDateTime());
        session.setRfIdTag(rfIdTag);
        return repository.save(session);
    }

    @Transactional
    @Override
    public void endSession(EndSessionRequest request) {
        ChargingSession session = getSessionById(request.getSessionId());

        if (session.getEndTime().equals(request.getDateTime())) {
            //already finished, just return
            return;
        }
        session.setEndTime(request.getDateTime());
        connectorService.updateMeterValue(session.getChargeConnector(), request.getMeterValue());
        repository.save(session);
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
