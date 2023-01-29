package nz.netvalue.domain.service.impl;

import nz.netvalue.persistence.repository.ChargingSessionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;

@SpringBootTest(classes = ChargingSessionServiceImpl.class)
@DisplayName("Test service works with charging session")
class ChargingSessionServiceImplTest {

    @Autowired
    private ChargingSessionServiceImpl sut;

    @MockBean
    private ChargingSessionRepository repository;

    @Test
    @DisplayName("Call find all sessions method if date period not filled")
    void shouldCallFindAll() {
        sut.getChargeSessions(null, null);

        verify(repository).findAll();
    }

    @Test
    @DisplayName("Call find by date period method, if date period filled")
    void shouldCallFindByDatePeriod() {
        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();
        sut.getChargeSessions(dateFrom, dateTo);

        verify(repository).findByDatePeriod(dateFrom, dateTo);
    }
}