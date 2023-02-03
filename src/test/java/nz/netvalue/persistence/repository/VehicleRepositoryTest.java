package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.Vehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DisplayName("Test repository works with vehicles")
class VehicleRepositoryTest {

    @Autowired
    private VehicleRepository sut;

    @Test
    @DisplayName("Should return existing vehicle")
    void shouldReturnExistingVehicle() {
        Optional<Vehicle> actual = sut.findByRegistrationPlate("343-738");

        assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("Should return empty if vehicle not found")
    void shouldReturnEmptyWhenVehicleNotFound() {
        Optional<Vehicle> actual = sut.findByRegistrationPlate("343-");

        assertTrue(actual.isEmpty());
    }
}