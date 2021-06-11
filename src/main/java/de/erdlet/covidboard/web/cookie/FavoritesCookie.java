package de.erdlet.covidboard.web.cookie;

import javax.ws.rs.core.NewCookie;

import de.erdlet.covidboard.web.Cookies;

public final class FavoritesCookie extends NewCookie {

    private static final int THREE_YEARS_IN_SECONDS = 94_670_856;

    public FavoritesCookie(final String value) {
        super(Cookies.CB_FAVORITES, value, null, "", 1, "Cookie for storing favorites at covidboard.de", THREE_YEARS_IN_SECONDS, true);
    }
}
