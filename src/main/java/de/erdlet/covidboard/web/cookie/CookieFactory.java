package de.erdlet.covidboard.web.cookie;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

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

    public FavoritesCookie createForValue(final String value) {
        return FavoritesCookie.create(value, favoritesCookiePath, favoritesCookieDomain, secure);
    }
}
