package nz.netvalue.domain.service.impl;

import nz.netvalue.domain.exception.ResourceNotFoundException;
import nz.netvalue.persistence.model.RfIdTag;
import nz.netvalue.persistence.repository.RfidTagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RfidTagServiceImpl.class)
@DisplayName("Test Rfid tag service")
class RfidTagServiceImplTest {

    private static final UUID NUMBER = UUID.randomUUID();

    @Autowired
    private RfidTagServiceImpl sut;

    @MockBean
    private RfidTagRepository repository;

    @Test
    @DisplayName("If RFID tag exists then it returns")
    void shouldReturnRfIdTagByNumber() {
        when(repository.findByTagNumber(NUMBER)).thenReturn(Optional.of(createTag()));
        RfIdTag actual = sut.getByUUID(NUMBER);

        assertEquals(NUMBER, actual.getTagNumber());
    }

    @Test
    @DisplayName("If RFID tag not exists then throws an exception")
    void shouldThrow() {
        when(repository.findByTagNumber(NUMBER)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> sut.getByUUID(NUMBER));
    }

    private static RfIdTag createTag() {
        RfIdTag rfIdTag = new RfIdTag();
        rfIdTag.setTagNumber(NUMBER);
        return rfIdTag;
    }
}