package nz.netvalue.persistence.repository;

import nz.netvalue.persistence.model.RfIdTag;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@DisplayName("Test repository works with RFID tags")
class RfidTagRepositoryTest {

    @Autowired
    private RfidTagRepository sut;

    @Test
    @DisplayName("Should return existing RFID tag")
    void shouldReturnExistingTag() {
        Optional<RfIdTag> actual = sut.findByTagNumber(UUID.fromString("9382134b-46f1-437f-b581-49c533a49661"));

        assertTrue(actual.isPresent());
    }

    @Test
    @DisplayName("Should return empty if RFID tag not found")
    void shouldReturnEmptyWhenTagNotFound() {
        Optional<RfIdTag> actual = sut.findByTagNumber(UUID.fromString("8382134b-46f1-437f-b581-49c533a49661"));

        assertTrue(actual.isEmpty());
    }

}