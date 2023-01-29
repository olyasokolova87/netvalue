package nz.netvalue.controller;

import nz.netvalue.controller.dto.ChargingSessionResponse;
import nz.netvalue.controller.mapper.ChargingSessionMapper;
import nz.netvalue.domain.service.ChargingSessionService;
import nz.netvalue.persistence.model.ChargingSession;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ChargingSessionApiDelegateImpl implements ChargingSessionsApiDelegate {

    private final ChargingSessionService chargingSessionService;
    private final ChargingSessionMapper sessionMapper;

    public ChargingSessionApiDelegateImpl(ChargingSessionService chargingSessionService,
                                          ChargingSessionMapper sessionMapper) {
        this.chargingSessionService = chargingSessionService;
        this.sessionMapper = sessionMapper;
    }

    @Override
    public ResponseEntity<List<ChargingSessionResponse>> getChargeSessions(LocalDate dateFrom, LocalDate dateTo) {
        List<ChargingSession> sessions = chargingSessionService.getChargeSessions(dateFrom, dateTo);

        List<ChargingSessionResponse> responses = sessionMapper.toResponseList(sessions);
        return ResponseEntity.ok(responses);
    }
}
