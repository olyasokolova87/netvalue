package nz.netvalue.controller;

import nz.netvalue.controller.dto.ChargingSessionResponse;
import nz.netvalue.controller.dto.StartSessionRequest;
import nz.netvalue.controller.mapper.ChargingSessionMapper;
import nz.netvalue.controller.utils.LocationBuilder;
import nz.netvalue.domain.service.ChargingSessionService;
import nz.netvalue.persistence.model.ChargingSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Component
public class ChargingSessionApiDelegateImpl implements ChargingSessionsApiDelegate {

    private final ChargingSessionService chargingSessionService;
    private final ChargingSessionMapper sessionMapper;
    private final LocationBuilder locationBuilder;

    public ChargingSessionApiDelegateImpl(ChargingSessionService chargingSessionService,
                                          ChargingSessionMapper sessionMapper,
                                          LocationBuilder locationBuilder) {
        this.chargingSessionService = chargingSessionService;
        this.sessionMapper = sessionMapper;
        this.locationBuilder = locationBuilder;
    }

    @Override
    public ResponseEntity<List<ChargingSessionResponse>> getChargeSessions(LocalDate dateFrom, LocalDate dateTo) {
        List<ChargingSession> sessions = chargingSessionService.getChargeSessions(dateFrom, dateTo);

        List<ChargingSessionResponse> responses = sessionMapper.toResponseList(sessions);
        return ResponseEntity.ok(responses);
    }

    @Override
    public ResponseEntity<Void> startSession(StartSessionRequest request) {
        ChargingSession created = chargingSessionService.createSession(request);
        URI location = locationBuilder.build(created.getId());
        return ResponseEntity.created(location).build();
    }
}
