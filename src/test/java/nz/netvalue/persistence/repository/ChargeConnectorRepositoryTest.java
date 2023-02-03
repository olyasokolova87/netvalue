package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.ChargeConnector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DisplayName("Test repository works with charge connectors")
class ChargeConnectorRepositoryTest {

    @Autowired
    private ChargeConnectorRepository sut;

    @Test
    @DisplayName("Should return existing charge connector")
    void shouldReturnExistingConnector() {
        Optional<ChargeConnector> actual = sut.findByChargePointAndNumber("number1", 1L);

        assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("Should return empty if charge connector not found")
    void shouldReturnEmptyWhenConnectorNotFound() {
        Optional<ChargeConnector> actual = sut.findByChargePointAndNumber("1", 1L);

        assertTrue(actual.isEmpty());
    }

    @Test
    @DisplayName("Should return true if charge connector exists")
    void shouldReturnTrueWhenConnectorExists() {
        Integer count = sut.countByChargePointIdAndConnectorNumber(1L, 1L);

        assertEquals(1, count);
    }

    @Test
    @DisplayName("Should return false if charge connector not exists")
    void shouldReturnFalseWhenConnectorNotExists() {
        Integer count = sut.countByChargePointIdAndConnectorNumber(1L, 13L);

        assertEquals(0, count);
    }
}