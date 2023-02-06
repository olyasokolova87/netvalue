package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.model.ChargingSession;
import nz.netvalue.persistence.model.RfIdTag;
import nz.netvalue.persistence.model.Vehicle;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DisplayName("Test repository works with charging session")
class ChargingSessionRepositoryTest {

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private ChargingSessionRepository sut;

    @Test
    @DisplayName("Should return empty if started session not found")
    void shouldReturnEmptyIfStartedSessionNotFound() {
        RfIdTag rfIdTag = testEntityManager.find(RfIdTag.class, 1L);
        Vehicle vehicle = testEntityManager.find(Vehicle.class, 1L);
        Optional<ChargingSession> startedSession = sut.findStartedSession(rfIdTag, vehicle);
        assertTrue(startedSession.isEmpty());
    }

    @Test
    @DisplayName("Should return started session if it exists")
    void shouldReturnStartedSession() {
        RfIdTag rfIdTag = testEntityManager.find(RfIdTag.class, 1L);
        Vehicle vehicle = testEntityManager.find(Vehicle.class, 1L);
        ChargingSession session = createSession(rfIdTag, vehicle);
        testEntityManager.persist(session);

        Optional<ChargingSession> startedSession = sut.findStartedSession(rfIdTag, vehicle);
        assertTrue(startedSession.isPresent());
    }

    @Test
    @DisplayName("Should find two session if period is null")
    void shouldFindTwoSessions() {
        RfIdTag rfIdTag = testEntityManager.find(RfIdTag.class, 1L);
        Vehicle vehicle = testEntityManager.find(Vehicle.class, 1L);

        ChargingSession successSession = createSession(rfIdTag, vehicle);
        successSession.setEndTime(LocalDateTime.now());
        testEntityManager.persist(successSession);

        ChargingSession errorSession = createSession(rfIdTag, vehicle);
        errorSession.setErrorMessage("Some error");
        testEntityManager.persist(errorSession);

        List<ChargingSession> actualList = sut.findByDatePeriod(null, null);
        assertEquals(2, actualList.size());
    }

    @Test
    @DisplayName("Should find one session if period is filled")
    void shouldFindOneSessionInPeriod() {
        RfIdTag rfIdTag = testEntityManager.find(RfIdTag.class, 1L);
        Vehicle vehicle = testEntityManager.find(Vehicle.class, 1L);

        ChargingSession yesterdaySession = createSession(rfIdTag, vehicle);
        yesterdaySession.setStartTime(LocalDateTime.now().minusDays(1).minusMinutes(2));
        yesterdaySession.setEndTime(LocalDateTime.now().minusDays(2));
        testEntityManager.persist(yesterdaySession);

        ChargingSession todaySession = createSession(rfIdTag, vehicle);
        todaySession.setStartTime(LocalDateTime.now().minusMinutes(2));
        todaySession.setEndTime(LocalDateTime.now());
        testEntityManager.persist(todaySession);

        List<ChargingSession> actualList = sut.findByDatePeriod(LocalDateTime.now().minusDays(2),
                LocalDateTime.now());
        assertEquals(1, actualList.size());
    }

    private ChargingSession createSession(RfIdTag rfIdTag, Vehicle vehicle) {
        ChargeConnector connector = testEntityManager.find(ChargeConnector.class, 1L);
        ChargingSession session = new ChargingSession();
        session.setVehicle(vehicle);
        session.setRfIdTag(rfIdTag);
        session.setChargeConnector(connector);
        session.setStartTime(LocalDateTime.now().minusMinutes(2));
        return session;
    }
}