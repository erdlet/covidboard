package de.erdlet.covistat.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A utility class which handles different formattings.
 *
 * @author erdlet
 *
 */
public final class Formatters {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public static String formatDateToGermanLayout(final LocalDate date) {
        return FORMATTER.format(date);
    }

    public static String formatDoubleToStringWithTwoDecimalAfterComma(final Double number) {
        return String.format("%.2f", number);
    }

    private Formatters() {
    }
}
