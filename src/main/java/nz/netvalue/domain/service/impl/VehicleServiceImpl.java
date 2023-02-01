package nz.netvalue.domain.service.impl;

import nz.netvalue.domain.exception.ResourceNotFoundException;
import nz.netvalue.domain.service.VehicleService;
import nz.netvalue.persistence.model.Vehicle;
import nz.netvalue.persistence.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static java.lang.String.format;

@Service
public class VehicleServiceImpl implements VehicleService {

    private final VehicleRepository repository;

    public VehicleServiceImpl(VehicleRepository repository) {
        this.repository = repository;
    }

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
