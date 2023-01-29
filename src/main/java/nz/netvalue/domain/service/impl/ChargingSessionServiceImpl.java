package nz.netvalue.domain.service.impl;

import nz.netvalue.controller.dto.StartSessionRequest;
import nz.netvalue.domain.service.ChargeConnectorService;
import nz.netvalue.domain.service.ChargingSessionService;
import nz.netvalue.domain.service.RfidTagService;
import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.model.ChargingSession;
import nz.netvalue.persistence.model.RfIdTag;
import nz.netvalue.persistence.repository.ChargingSessionRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

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
    public ChargingSession createSession(StartSessionRequest request) {
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
}
