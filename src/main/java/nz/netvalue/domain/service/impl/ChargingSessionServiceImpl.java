package nz.netvalue.domain.service.impl;

import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.model.EndSessionRequest;
import nz.netvalue.controller.model.StartSessionRequest;
import nz.netvalue.domain.service.ChargingSessionService;
import nz.netvalue.domain.service.RfidTagService;
import nz.netvalue.domain.service.VehicleService;
import nz.netvalue.domain.service.connector.GetConnectorService;
import nz.netvalue.domain.service.connector.UpdateConnectorService;
import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.exception.SessionAlreadyStartedException;
import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.model.ChargingSession;
import nz.netvalue.persistence.model.RfIdTag;
import nz.netvalue.persistence.model.Vehicle;
import nz.netvalue.persistence.repository.ChargingSessionRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class ChargingSessionServiceImpl implements ChargingSessionService {

    private final ChargingSessionRepository repository;
    private final GetConnectorService getConnectorService;
    private final UpdateConnectorService updateConnectorService;
    private final RfidTagService rfidTagService;
    private final VehicleService vehicleService;

    @Override
    public List<ChargingSession> getChargeSessions(LocalDate dateFrom, LocalDate dateTo) {
        LocalDateTime startPeriod = dateFrom != null ? dateFrom.atStartOfDay() : null;
        LocalDate endDate = dateTo != null ? dateTo : LocalDate.now();
        return repository.findByDatePeriod(startPeriod, endDate.atTime(LocalTime.MAX));
    }

    @Override
    public ChargingSession startSession(StartSessionRequest request) {
        ChargeConnector connector = getConnectorService.getConnector(
                request.getPointSerialNumber(),
                request.getConnectorNumber());
        RfIdTag rfIdTag = rfidTagService.getByUUID(UUID.fromString(request.getRfIdTagNumber()));
        Vehicle vehicle = vehicleService.getByRegistrationPlate(request.getVehicleRegistrationPlate());
        checkSessionAlreadyStarted(request, rfIdTag, vehicle);

        ChargingSession session = createSession(request, connector, rfIdTag, vehicle);
        return repository.save(session);
    }

    private void checkSessionAlreadyStarted(StartSessionRequest request, RfIdTag rfIdTag, Vehicle vehicle) {
        if (repository.findStartedSession(rfIdTag, vehicle).isPresent()) {
            String message = format("Vehicle with reg.plate [%s] is already charging",
                    request.getVehicleRegistrationPlate());
            throw new SessionAlreadyStartedException(message);
        }
    }

    private static ChargingSession createSession(StartSessionRequest request,
                                                 ChargeConnector connector,
                                                 RfIdTag rfIdTag,
                                                 Vehicle vehicle) {
        ChargingSession session = new ChargingSession();
        session.setChargeConnector(connector);
        session.setStartTime(request.getStartTime());
        session.setRfIdTag(rfIdTag);
        session.setVehicle(vehicle);
        return session;
    }

    @Transactional
    @Override
    public void endSession(EndSessionRequest request) {
        ChargingSession session = getSessionById(request.getSessionId());
        if (session.getEndTime() != null && session.getEndTime().equals(request.getEndTime())) {
            //already finished, just return
            return;
        }

        session.setEndTime(request.getEndTime());
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
