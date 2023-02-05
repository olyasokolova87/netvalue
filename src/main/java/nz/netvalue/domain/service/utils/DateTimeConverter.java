package nz.netvalue.domain.service.utils;

import nz.netvalue.domain.service.utils.exception.DateParseException;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * First solution: generate all variations of patterns and check all
 * <p>
 * Disadvantages:
 * - Have to add new pattern if it's not included
 * - Time zone have to process separately
 * <p>
 * My suggestions always put date format in contract :)
 */
public final class DateTimeConverter {

    private static final String BASE_SEPARATOR = "\\$";
    private static final String[] DATE_SEPARATORS = {"-", "/", "."};
    private static final String[] TIME_SEPARATORS = {":", "/", "."};

    private static final String[] SOURCE_DATE_PATTERNS = {
            "yyyy$MM$dd",
            "dd$MM$yyyy",
            "MM$dd$yyyy",
            "MM$yyyy",
            "yyyy$MM",
            "yyyy"
    };

    private static final String[] SOURCE_TIME_PATTERNS = {
            "HH$mm$ss.SSS",
            "HH$mm$ss",
            "HH$mm"
    };

    private static final Set<String> PATTERNS = new LinkedHashSet<>();

    static {
        // add date patterns
        Set<String> datePatterns = buildPatterns(SOURCE_DATE_PATTERNS, DATE_SEPARATORS);
        PATTERNS.addAll(datePatterns);
        // add time patterns
        Set<String> timePatterns = buildPatterns(SOURCE_TIME_PATTERNS, TIME_SEPARATORS);
        PATTERNS.addAll(timePatterns);

        // add merged patterns with date and time
        for (String datePattern : datePatterns) {
            for (String timePattern : timePatterns) {
                addMerged(datePattern, timePattern);
                addMerged(timePattern, datePattern);
            }
        }
    }

    private static Set<String> buildPatterns(String[] sourceDatePatterns, String[] separatedDate) {
        Set<String> datePatterns = new LinkedHashSet<>();
        for (String pattern : sourceDatePatterns) {
            for (String s : separatedDate) {
                String value = pattern.replaceAll(BASE_SEPARATOR, s);
                datePatterns.add(value);
            }
        }
        return datePatterns;
    }

    private static void addMerged(String datePattern, String timePattern) {
        PATTERNS.add(datePattern + ' ' + timePattern);
        PATTERNS.add(datePattern + "'T'" + timePattern);
    }

    /**
     * Parse date from string
     *
     * @param dateString string with date
     * @return date
     */
    public LocalDateTime parse(String dateString) {
        try {
            Date date = DateUtils.parseDateStrictly(dateString, PATTERNS.toArray(String[]::new));
            return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        } catch (ParseException e) {
            throw new DateParseException(e);
        }
    }
}
