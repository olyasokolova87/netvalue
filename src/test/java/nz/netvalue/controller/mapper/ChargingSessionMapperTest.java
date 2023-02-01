package nz.netvalue.controller.mapper;

import nz.netvalue.controller.dto.ChargingSessionResponse;
import nz.netvalue.persistence.model.ChargeConnector;
import nz.netvalue.persistence.model.ChargingSession;
import nz.netvalue.persistence.model.RfIdTag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {ChargingSessionMapperImpl.class, MapperUtils.class})
@DisplayName("Test mapping of charging sessions")
class ChargingSessionMapperTest {

    private static final LocalDateTime START_TIME = LocalDateTime.now().minusHours(1);
    private static final LocalDateTime END_TIME = LocalDateTime.now().minusMinutes(20);
    private static final String TAG_NAME = "rfid";
    private static final Long CONNECTOR_NUMBER = 2L;
    private static final UUID TAG_NUMBER = UUID.randomUUID();
    @Autowired
    private ChargingSessionMapper sut;

    @Test
    @DisplayName("Map charging session list from for response correctly")
    void shouldMapResponseList() {
        ChargingSession chargingSession = createSession();
        List<ChargingSessionResponse> actualList = sut.toResponseList(Collections.singletonList(chargingSession));

        assertEquals(1, actualList.size());
        ChargingSessionResponse actualResponse = actualList.get(0);
        assertNotNull(actualResponse.getRfIdTag());
        assertNotNull(actualResponse.getChargeConnector());
        assertEquals(CONNECTOR_NUMBER, actualResponse.getChargeConnector().getConnectorNumber());
        assertEquals(TAG_NAME, actualResponse.getRfIdTag().getTagName());
        assertEquals(TAG_NUMBER.toString(), actualResponse.getRfIdTag().getTagNumber());
        assertEquals(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(START_TIME),
                actualResponse.getStartTime());
        assertEquals(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(END_TIME),
                actualResponse.getEndTime());
    }

    private static ChargingSession createSession() {
        ChargingSession chargingSession = new ChargingSession();
        chargingSession.setChargeConnector(createConnector());
        chargingSession.setRfIdTag(createRFID());
        chargingSession.setStartTime(START_TIME);
        chargingSession.setEndTime(END_TIME);
        return chargingSession;
    }

    private static RfIdTag createRFID() {
        RfIdTag rfIdTag = new RfIdTag();
        rfIdTag.setTagNumber(TAG_NUMBER);
        rfIdTag.setTagName(TAG_NAME);
        return rfIdTag;
    }

    private static ChargeConnector createConnector() {
        ChargeConnector chargeConnector = new ChargeConnector();
        chargeConnector.setConnectorNumber(CONNECTOR_NUMBER);
        return chargeConnector;
    }
}