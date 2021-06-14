package de.erdlet.covidboard.web.cookie;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import javax.ws.rs.core.NewCookie;

import de.erdlet.covidboard.web.Cookies;

public final class FavoritesCookie extends NewCookie {

    private static final int THREE_YEARS_IN_SECONDS = 94_670_856;
    private static final String COMMENT = "Cookie for storing favorites at covidboard.de";

    public static FavoritesCookie create(final String value, final String path, final String domain, final boolean secure) {
        if (value == null || value.isBlank()) {
            return new FavoritesCookie(path, domain, secure);
        }

        return new FavoritesCookie(value, path, domain, secure);
    }

    /**
     * Constructor to create or update a {@link FavoritesCookie}.
     */
    private FavoritesCookie(final String value, final String path, final String domain, final boolean secure) {
        super(Cookies.CB_FAVORITES, value, path, domain, 1, COMMENT, THREE_YEARS_IN_SECONDS, null, secure, true);
    }

    /**
     * Constructor for a {@link FavoritesCookie} going to be deleted.
     */
    private FavoritesCookie(final String path, final String domain, final boolean secure) {
        super(Cookies.CB_FAVORITES, null, path, domain, 1, COMMENT, 0, dateInThePast(), secure, true);
    }

    private static Date dateInThePast() {
        return Date.from(LocalDateTime.now().minusYears(1).atZone(ZoneId.of("Europe/Berlin")).toInstant());
    }
}
