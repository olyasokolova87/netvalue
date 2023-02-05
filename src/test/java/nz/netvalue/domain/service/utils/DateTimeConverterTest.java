package nz.netvalue.domain.service.utils;

import nz.netvalue.domain.service.utils.exception.DateParseException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DateTimeConverterTest {

    private final DateTimeConverter sut = new DateTimeConverter();

    @Test
    @DisplayName("Should fail if incorrect date")
    void shouldFailIfIncorrectDate() {
        assertThrows(DateParseException.class, () -> sut.parse("3434xcx"));
    }

    @ParameterizedTest
    @DisplayName("Should return parsed date")
    @MethodSource("dateTimeMethodSource")
    void shouldReturnParsedDate(String dateSource, LocalDateTime actual) {
        assertEquals(actual, sut.parse(dateSource));
    }

    public static Stream<Arguments> dateTimeMethodSource() {
        return Stream.of(
                Arguments.of("2020.03.05T13:10:04", LocalDateTime.of(2020, 3, 5, 13, 10, 4)),
                Arguments.of("2020-03-05T13.10.04", LocalDateTime.of(2020, 3, 5, 13, 10, 4)),
                Arguments.of("2020/03/05T13.10.04", LocalDateTime.of(2020, 3, 5, 13, 10, 4)),
                Arguments.of("2020.03.05T13:10", LocalDateTime.of(2020, 3, 5, 13, 10, 0)),
                Arguments.of("2020.03.05 13:10:04", LocalDateTime.of(2020, 3, 5, 13, 10, 4)),
                Arguments.of("2020-03-05 13:10:04", LocalDateTime.of(2020, 3, 5, 13, 10, 4)),
                Arguments.of("2020/03/05 13:10:04", LocalDateTime.of(2020, 3, 5, 13, 10, 4)),
                Arguments.of("2020.03.05 13:10", LocalDateTime.of(2020, 3, 5, 13, 10, 0)),
                Arguments.of("2020-03-05 13:10", LocalDateTime.of(2020, 3, 5, 13, 10, 0)),
                Arguments.of("13:10 2020-03-05", LocalDateTime.of(2020, 3, 5, 13, 10, 0)),
                Arguments.of("13.10 2020-03-05", LocalDateTime.of(2020, 3, 5, 13, 10, 0)),
                Arguments.of("13/10 2020.03.05", LocalDateTime.of(2020, 3, 5, 13, 10, 0)),
                Arguments.of("2020/03/05 13:10", LocalDateTime.of(2020, 3, 5, 13, 10, 0)),
                Arguments.of("2020.03.01", LocalDateTime.of(2020, 3, 1, 0, 0, 0)),
                Arguments.of("2020-03-01", LocalDateTime.of(2020, 3, 1, 0, 0, 0)),
                Arguments.of("2020/03/01", LocalDateTime.of(2020, 3, 1, 0, 0, 0)),
                Arguments.of("2020.03", LocalDateTime.of(2020, 3, 1, 0, 0, 0)),
                Arguments.of("2020-03", LocalDateTime.of(2020, 3, 1, 0, 0, 0)),
                Arguments.of("2020/03", LocalDateTime.of(2020, 3, 1, 0, 0, 0)),
                Arguments.of("2020", LocalDateTime.of(2020, 1, 1, 0, 0, 0)),
                Arguments.of("15:30", LocalDateTime.of(1970, 1, 1, 15, 30, 0)),
                Arguments.of("15/30", LocalDateTime.of(1970, 1, 1, 15, 30, 0)),
                Arguments.of("15.30", LocalDateTime.of(1970, 1, 1, 15, 30, 0)),
                Arguments.of("15:30:24", LocalDateTime.of(1970, 1, 1, 15, 30, 24)),
                Arguments.of("15/30/24", LocalDateTime.of(1970, 1, 1, 15, 30, 24)),
                Arguments.of("15.30.24", LocalDateTime.of(1970, 1, 1, 15, 30, 24)),
                Arguments.of("15:30:24.567", LocalDateTime.of(1970, 1, 1, 15, 30, 24, 567000000)),
                Arguments.of("15.30.24.567", LocalDateTime.of(1970, 1, 1, 15, 30, 24, 567000000)),
                Arguments.of("15/30/24.567", LocalDateTime.of(1970, 1, 1, 15, 30, 24, 567000000))
        );
    }
}