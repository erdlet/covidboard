package de.erdlet.covidboard.web;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * A utility class providing formatters for different values shown in the UI. This probably could be done by the used
 * template engine, but as formatting isn't something every engine can do, it's done server side.
 *
 * @author erdlet
 *
 */
public final class Formatters {

    private static final DateTimeFormatter GERMAN_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    /**
     * Formats a date to a String with german layout.
     *
     * @param date the date which shall be formatted as String
     * @return the date as string with format <b>dd.MM.yyyy</b>
     */
    public static String formatDateToGermanLayout(final LocalDate date) {
        return GERMAN_DATE_FORMATTER.format(date);
    }

    /**
     * Formats a Double to a String with two decimal places.
     *
     * @param number the number to format
     * @return the number formatted as string only showing two decimal places
     */
    public static String formatDoubleToStringWithTwoDecimalPlaces(final Double number) {
        return String.format("%.2f", number);
    }

    /**
     * Private constructor because this class doesn't need to be instantiated directly.
     */
    private Formatters() {
    }
}
