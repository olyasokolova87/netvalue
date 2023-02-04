package nz.netvalue.domain.service.session.impl;

import lombok.RequiredArgsConstructor;
import nz.netvalue.controller.model.StartSessionRequest;
import nz.netvalue.domain.service.connector.GetConnectorService;
import nz.netvalue.domain.service.rfidtag.RfidTagService;
import nz.netvalue.domain.service.session.StartSessionService;
import nz.netvalue.domain.service.vehicle.VehicleService;
import nz.netvalue.exception.SessionAlreadyStartedException;
import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.model.ChargingSession;
import nz.netvalue.persistence.model.RfIdTag;
import nz.netvalue.persistence.model.Vehicle;
import nz.netvalue.persistence.repository.ChargingSessionRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class StartSessionServiceImpl implements StartSessionService {

    private final ChargingSessionRepository repository;
    private final GetConnectorService getConnectorService;
    private final RfidTagService rfidTagService;
    private final VehicleService vehicleService;

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
}
