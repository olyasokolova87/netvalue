package nz.netvalue.domain.service.utils;

import com.mdimension.jchronic.Chronic;
import nz.netvalue.domain.service.utils.exception.DateParseException;

import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * NLP Solution used JChronic lib
 * <p>
 * Disadvantages:
 * - no guarantees all patterns can be processed
 * - no guarantees known pattern can be processed correctly
 */
public final class DateTimeConverterChronic {

    /**
     * Parse string to date
     *
     * @param dateString date in string
     * @return date time
     */
    public LocalDateTime parse(String dateString) {
        Calendar calendar = Chronic.parse(dateString).getEndCalendar();
        if (calendar == null) {
            throw new DateParseException();
        }
        return LocalDateTime.ofInstant(calendar.toInstant(), calendar.getTimeZone().toZoneId());
    }
}
