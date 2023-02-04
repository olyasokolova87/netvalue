package nz.netvalue.domain.service.impl.session;

import nz.netvalue.persistence.model.ChargingSession;
import nz.netvalue.persistence.repository.ChargingSessionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Test service gets charging session")
@SpringBootTest(classes = GetSessionServiceImpl.class)
class GetSessionServiceImplTest {

    @Autowired
    private GetSessionServiceImpl sut;

    @MockBean
    private ChargingSessionRepository repository;

    @Test
    @DisplayName("Should find all sessions by period")
    void shouldFindSessionsByPeriod() {
        when(repository.findByDatePeriod(any(), any()))
                .thenReturn(Collections.singletonList(new ChargingSession()));

        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();
        List<ChargingSession> actual = sut.getChargeSessions(dateFrom, dateTo);

        verify(repository).findByDatePeriod(dateFrom.atStartOfDay(), dateTo.atTime(LocalTime.MAX));
        assertEquals(1, actual.size());
    }
}