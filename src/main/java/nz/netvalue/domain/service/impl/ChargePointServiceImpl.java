package nz.netvalue.domain.service.impl;

import nz.netvalue.domain.exception.ResourceNotFoundException;
import nz.netvalue.domain.service.ChargePointService;
import nz.netvalue.persistence.model.ChargePoint;
import nz.netvalue.persistence.repository.ChargePointRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static java.lang.String.format;

@Component
public class ChargePointServiceImpl implements ChargePointService {

    private final ChargePointRepository chargePointRepository;

    public ChargePointServiceImpl(ChargePointRepository chargePointRepository) {
        this.chargePointRepository = chargePointRepository;
    }

    @Override
    public ChargePoint getChargePoint(String serialNumber) {
        Optional<ChargePoint> optional = chargePointRepository.findBySerialNumber(serialNumber);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException(
                    format("Charge point with serialNumber [%s] not exists", serialNumber));
        }
        return optional.get();
    }
}
