package nz.netvalue.domain.service.utils;

import nz.netvalue.domain.service.utils.exception.DateParseException;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * Easy solution: to check all known patterns
 * <p>
 * Disadvantages:
 * - Have to add new format if it is not included
 * - At start no guarantee all patterns will be included
 * <p>
 * My suggestions always put date format in contract, and you don't have to build a nlp :)
 */
public class DateTimeConverter {

    private final String[] patterns = {
            "yyyy-MM-dd HH:mm",
            "yyyy/MM/dd",
            // here have to add many and many patterns
    };

    public LocalDateTime parse(String dateString) {
        try {
            Date date = DateUtils.parseDate(dateString, patterns);
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        } catch (ParseException e) {
            throw new DateParseException(e);
        }
    }

}
