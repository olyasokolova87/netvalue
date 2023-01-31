package nz.netvalue.controller;

import nz.netvalue.controller.dto.ChargingSessionResponse;
import nz.netvalue.controller.dto.EndSessionRequest;
import nz.netvalue.controller.dto.StartSessionRequest;
import nz.netvalue.controller.mapper.ChargingSessionMapper;
import nz.netvalue.controller.utils.LocationBuilder;
import nz.netvalue.domain.service.ChargingSessionService;
import nz.netvalue.persistence.model.ChargingSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ChargingSessionResponse>> getChargeSessions(LocalDate dateFrom, LocalDate dateTo) {
        List<ChargingSession> sessions = chargingSessionService.getChargeSessions(dateFrom, dateTo);

        List<ChargingSessionResponse> responses = sessionMapper.toResponseList(sessions);
        return ResponseEntity.ok(responses);
    }

    @Override
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> startSession(StartSessionRequest request) {
        ChargingSession created = chargingSessionService.startSession(request);
        URI location = locationBuilder.build(created.getId());
        return ResponseEntity.created(location).build();
    }

    @Override
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<Void> endSession(EndSessionRequest request) {
        chargingSessionService.endSession(request);
        return ResponseEntity.ok().build();
    }
}
