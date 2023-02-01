package nz.netvalue.domain.service.util;

import nz.netvalue.domain.service.util.exception.DateParseException;
import org.ocpsoft.prettytime.nlp.PrettyTimeParser;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

/**
 * NLP Solution used PrettyTime lib
 * <p>
 * Disadvantages:
 * - no guarantees all patterns can be processed
 * - no guarantees known pattern can be processed correctly
 */
public final class DateTimeConverterPrettyTime {

    private final PrettyTimeParser prettyTimeParser = new PrettyTimeParser();

    /**
     * Parse string to date
     *
     * @param dateString date in string
     * @return date time
     */
    public LocalDateTime parse(String dateString) {
        List<Date> dates = prettyTimeParser.parse(dateString);
        if (dates.isEmpty()) {
            throw new DateParseException();
        }
        Date date = dates.get(0);
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}
