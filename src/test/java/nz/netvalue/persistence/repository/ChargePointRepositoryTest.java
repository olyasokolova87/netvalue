package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.ChargePoint;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DisplayName("Test repository works with charge points")
class ChargePointRepositoryTest {

    @Autowired
    private ChargePointRepository sut;

    @Test
    @DisplayName("Should return existing charging point")
    void shouldReturnExistingPoint() {
        Optional<ChargePoint> actual = sut.findBySerialNumber("number1");

        assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("Should return empty if charging point not found")
    void shouldReturnEmptyWhenPointNotFound() {
        Optional<ChargePoint> actual = sut.findBySerialNumber("1");

        assertTrue(actual.isEmpty());
    }
}