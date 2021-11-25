package de.erdlet.covidboard.web.cookie;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.core.NewCookie;

import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * The {@link CookieFactory} creates Cookies with specific configurations to enable the usage on different
 * environments.<br>
 * <br>
 * All possible settings are maintained in the <code>microprofile-config.properties</code> file in
 * <code>src/main/resources/META-INF</code>.
 *
 * @author erdlet
 *
 */
@ApplicationScoped
public class CookieFactory {

    @Inject
    @ConfigProperty(name = "cookies.secure")
    private boolean secure;

    @Inject
    @ConfigProperty(name = "cookies.cb_favorites.path")
    private String favoritesCookiePath;

    @Inject
    @ConfigProperty(name = "cookies.cb_favorites.domain")
    private String favoritesCookieDomain;

    /**
     * Creates the FavoritesCookie for a specific value.
     *
     * @param value the value the cookie shall have. Can be empty. In case the value is empty, the Cookie will be marked for
     * deletion.
     * @return either a Cookie with renewed value or, in case of an empty value, a cookie which is marked for deletion.
     */
    public NewCookie createForValue(final String value) {
        return FavoritesCookie.create(value, favoritesCookiePath, favoritesCookieDomain, secure);
    }
}
