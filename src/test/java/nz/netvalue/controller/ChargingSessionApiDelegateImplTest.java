package nz.netvalue.controller;

import nz.netvalue.controller.dto.ChargingSessionResponse;
import nz.netvalue.controller.mapper.ChargingSessionMapper;
import nz.netvalue.domain.service.ChargingSessionService;
import nz.netvalue.persistence.model.ChargingSession;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = ChargingSessionApiDelegateImpl.class)
class ChargingSessionApiDelegateImplTest {

    @Autowired
    private ChargingSessionApiDelegateImpl sut;

    @MockBean
    private ChargingSessionService service;

    @MockBean
    private ChargingSessionMapper mapper;

    @Test
    @DisplayName("Get list of charging sessions")
    void shouldReturnSessionListResponse() {
        LocalDate dateFrom = LocalDate.now().minusDays(1);
        LocalDate dateTo = LocalDate.now();
        when(service.getChargeSessions(dateFrom, dateTo))
                .thenReturn(Collections.singletonList(new ChargingSession()));
        when(mapper.toResponseList(anyList()))
                .thenReturn(Collections.singletonList(new ChargingSessionResponse()));

        ResponseEntity<List<ChargingSessionResponse>> actual = sut.getChargeSessions(dateFrom, dateTo);

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertNotNull(actual.getBody());
        assertEquals(1, actual.getBody().size());
    }
}