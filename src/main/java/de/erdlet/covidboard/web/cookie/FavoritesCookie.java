package de.erdlet.covidboard.web.cookie;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ws.rs.core.NewCookie;

import de.erdlet.covidboard.domain.County;

/**
 * The {@link FavoritesCookie} is a technical necessary cookie for storing the favorites of an user. To avoid user
 * accounts for storing these information, a cookie is used.<br>
 * <br>
 * In general, the cookie has the following attributes:
 * <ul>
 * <li>Name: cb_favorites</li>
 * <li>Max Age: three years</li>
 * <li>Value: a list of concatenated AGS</li>
 * </ul>
 *
 * <b>Attention:</b> Without this cookie, the whole dashboard screen won't work.
 *
 * <b>Note on implementation:</b> Because the {@link NewCookie} creates a static delegate to the JAX-RS runtime, this
 * class doesn't extend it (anymore), as testing was not really possible without real infrastructure.
 *
 * @author erdlet
 *
 */
public final class FavoritesCookie {

    public static final String NAME = "cb_favorites";

    private static final int THREE_YEARS_IN_SECONDS = 94_670_856;
    private static final String COMMENT = "Cookie for storing favorites at covidboard.de";

    /**
     * Extract favorites from a received cookie's value.
     *
     * @param cookie the cookie received by the HTTP request
     * @return a list of AGS representing the users favored {@link County}s
     */
    public static List<String> extractFavoritesAsList(final String value) {

        return Stream.of(value.split("&")).collect(Collectors.toList());
    }

    /**
     * Formats a list of values into the correct format for the cookie's value.
     *
     * @param favorites the list of AGS representing the users favored {@link County}s
     * @return the AGS joined with a <code>&</code> into a single string
     */
    public static String formatFavoritesToCookieValue(final List<String> favorites) {
        Objects.requireNonNull(favorites, "favorites must not be null");

        return favorites.stream().collect(Collectors.joining("&"));
    }

    /**
     * Factory method for creating a new {@link NewCookie} as representation of the {@link FavoritesCookie}.
     *
     * @param value the value to be set. Can be null or empty.
     * @param path the path on which the cookie is valid
     * @param domain the domain on which the cookie is valid
     * @param secure flag to indicate if a secure cookie has to be used. Should only be false for local development
     * environments.
     * @return a {@link NewCookie} with new or updated values. In case the value is null or empty, the cookie will be marked
     * for deletion by setting max-age and expiration date to the necessary values.
     */
    public static NewCookie create(final String value, final String path, final String domain, final boolean secure) {
        if (value == null || value.isBlank()) {
            return new NewCookie(NAME, null, path, domain, 1, COMMENT, 0, dateInThePast(), secure, true);
        }

        return new NewCookie(NAME, value, path, domain, 1, COMMENT, THREE_YEARS_IN_SECONDS, null, secure, true);
    }

    private static Date dateInThePast() {
        return Date.from(LocalDateTime.now().minusYears(1).atZone(ZoneId.of("Europe/Berlin")).toInstant());
    }

    /**
     * Private constructor as this class only holds static methods.
     */
    private FavoritesCookie() {
    }
}
