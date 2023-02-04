package nz.netvalue.domain.service.vehicle.impl;

import lombok.RequiredArgsConstructor;
import nz.netvalue.domain.service.vehicle.VehicleService;
import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.Vehicle;
import nz.netvalue.persistence.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;

    @Override
    public Vehicle getByRegistrationPlate(String regPlate) {
        Optional<Vehicle> optional = repository.findByRegistrationPlate(regPlate);
        if (optional.isEmpty()) {
            String message = format("Vehicle with registration plate = [%s] not exists", regPlate);
            throw new ResourceNotFoundException(message);
        }
        return optional.get();
    }
}
