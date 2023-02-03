package nz.netvalue.domain.service.utils;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DateTimeConverterPrettyTimeTest {

    private final DateTimeConverterPrettyTime sut = new DateTimeConverterPrettyTime();

    @Test
    void testDate() {
        LocalDate actual = sut.parse("2020/03/01").toLocalDate();

        assertEquals(LocalDate.of(2020, 3, 1), actual);
    }

    @Test
    void testDateTime() {
        LocalDateTime actual = sut.parse("2020-03-05 13:10");

        assertEquals(LocalDateTime.of(2020, 3, 5, 13, 10), actual);
    }

}