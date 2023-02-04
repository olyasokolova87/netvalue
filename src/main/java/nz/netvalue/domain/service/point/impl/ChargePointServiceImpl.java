package nz.netvalue.domain.service.point.impl;

import lombok.RequiredArgsConstructor;
import nz.netvalue.domain.service.point.ChargePointService;
import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.ChargePoint;
import nz.netvalue.persistence.repository.ChargePointRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.String.format;

@Component
@RequiredArgsConstructor
public class ChargePointServiceImpl implements ChargePointService {

    private final ChargePointRepository chargePointRepository;

    @Override
    public ChargePoint getChargePoint(String serialNumber) {
        Optional<ChargePoint> optional = chargePointRepository.findBySerialNumber(serialNumber);
        if (optional.isEmpty()) {
            String message = format("Charge point with serial number = [%s] not exists", serialNumber);
            throw new ResourceNotFoundException(message);
        }
        return optional.get();
    }
}
