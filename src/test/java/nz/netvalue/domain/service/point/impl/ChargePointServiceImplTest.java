package nz.netvalue.domain.service.point.impl;

import nz.netvalue.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.ChargePoint;
import nz.netvalue.persistence.repository.ChargePointRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@DisplayName("Test service for charge points")
@SpringBootTest(classes = ChargePointServiceImpl.class)
class ChargePointServiceImplTest {

    private static final String SERIAL_NUMBER = "1";

    @Autowired
    private ChargePointServiceImpl sut;

    @MockBean
    private ChargePointRepository repository;

    @Test
    @DisplayName("Should return charge point")
    void shouldGetChargePoint() {
        when(repository.findBySerialNumber(SERIAL_NUMBER))
                .thenReturn(Optional.of(new ChargePoint()));

        ChargePoint chargePoint = sut.getChargePoint(SERIAL_NUMBER);
        assertNotNull(chargePoint);
    }

    @Test
    @DisplayName("Should fail if point not found")
    void shouldFailWhenChargePointNotFound() {
        when(repository.findBySerialNumber(SERIAL_NUMBER))
                .thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> sut.getChargePoint(SERIAL_NUMBER));
    }
}