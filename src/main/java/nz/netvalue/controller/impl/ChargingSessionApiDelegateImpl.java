package nz.netvalue.controller.impl;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.ChargingSessionsApiDelegate;
import nz.netvalue.controller.mapper.ChargingSessionMapper;
import nz.netvalue.controller.model.ChargingSessionResponse;
import nz.netvalue.controller.model.EndSessionRequest;
import nz.netvalue.controller.model.StartSessionRequest;
import nz.netvalue.controller.utils.LocationHeaderBuilder;
import nz.netvalue.domain.service.session.EndSessionService;
import nz.netvalue.domain.service.session.GetSessionService;
import nz.netvalue.domain.service.session.StartSessionService;
import nz.netvalue.persistence.model.ChargingSession;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ChargingSessionApiDelegateImpl implements ChargingSessionsApiDelegate {

    private final GetSessionService getChargeSessionService;
    private final StartSessionService startSessionService;
    private final EndSessionService endSessionService;
    private final ChargingSessionMapper sessionMapper;
    private final LocationHeaderBuilder locationHeaderBuilder;

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    @Timed(value = "get.charge.sessions")
    public ResponseEntity<List<ChargingSessionResponse>> getChargeSessions(LocalDate dateFrom, LocalDate dateTo) {
        List<ChargingSession> sessions = getChargeSessionService.getChargeSessions(dateFrom, dateTo);

        List<ChargingSessionResponse> responses = sessionMapper.toResponseList(sessions);
        if (sessions.isEmpty()) {
            return ResponseEntity.ok(responses);
        }
        ZonedDateTime lastModified = sessions.get(0).getLastModifiedDate().atZone(ZoneId.systemDefault());
        return ResponseEntity.ok().lastModified(lastModified).body(responses);
    }

    @Override
    @PreAuthorize("hasRole('CUSTOMER')")
    @Timed(value = "start.session")
    public ResponseEntity<Void> startSession(StartSessionRequest request) {
        ChargingSession created = startSessionService.startSession(request);
        URI location = locationHeaderBuilder.build(created.getId());
        return ResponseEntity.created(location).build();
    }

    @Override
    @PreAuthorize("hasRole('CUSTOMER')")
    @Timed(value = "end.session")
    public ResponseEntity<Void> endSession(Long sessionId, EndSessionRequest request) {
        endSessionService.endSession(sessionId, request);
        return ResponseEntity.ok().build();
    }
}
