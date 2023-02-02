package nz.netvalue.domain.service.impl;

import nz.netvalue.domain.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.Vehicle;
import nz.netvalue.persistence.repository.VehicleRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@DisplayName("Test service that works with vehicles")
@SpringBootTest(classes = VehicleServiceImpl.class)
class VehicleServiceImplTest {

    private static final String REG_PLATE = "123ty5";

    @Autowired
    private VehicleServiceImpl sut;

    @MockBean
    private VehicleRepository repository;

    @Test
    @DisplayName("If vehicle exists then it returns")
    void shouldReturnVehicleByRegPlate() {
        when(repository.findByRegistrationPlate(REG_PLATE)).thenReturn(Optional.of(createVehicle()));
        Vehicle actual = sut.getByRegistrationPlate(REG_PLATE);

        assertNotNull(actual);
        assertEquals(REG_PLATE, actual.getRegistrationPlate());
    }

    @Test
    @DisplayName("If vehicle not exists then throws an exception")
    void shouldThrow() {
        when(repository.findByRegistrationPlate(REG_PLATE)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sut.getByRegistrationPlate(REG_PLATE));
    }

    private static Vehicle createVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationPlate(REG_PLATE);
        return vehicle;
    }
}