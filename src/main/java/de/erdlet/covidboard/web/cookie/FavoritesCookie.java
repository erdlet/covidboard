package de.erdlet.covidboard.web.cookie;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.ws.rs.core.NewCookie;

import de.erdlet.covidboard.web.Cookies;

public final class FavoritesCookie extends NewCookie {

    private static final int THREE_YEARS_IN_SECONDS = 94_670_856;
    private static final String COMMENT = "Cookie for storing favorites at covidboard.de";

    public static FavoritesCookie createForValue(final String value) {
        if (value == null || value.isBlank()) {
            return new FavoritesCookie();
        }

        return new FavoritesCookie(value);
    }

    /**
     * Constructor to create or update a {@link FavoritesCookie}.
     *
     * @param value the value to be set.
     */
    private FavoritesCookie(final String value) {
        super(Cookies.CB_FAVORITES, value, "/covidboard/mvc", "", 1, COMMENT, THREE_YEARS_IN_SECONDS, null, false, true);
    }

    /**
     * Constructor for a {@link FavoritesCookie} going to be deleted.
     */
    private FavoritesCookie() {
        super(Cookies.CB_FAVORITES, null, "/covidboard/mvc", "", 1, COMMENT, 0, dateInThePast(), false, true);
    }

    private static Date dateInThePast() {
        return Date.from(LocalDateTime.now().minusYears(1).atZone(ZoneId.of("Europe/Berlin")).toInstant());
    }
}
