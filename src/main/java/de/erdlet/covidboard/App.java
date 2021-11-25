package de.erdlet.covidboard;

import java.util.Map;

import javax.mvc.security.Csrf;
import javax.mvc.security.Csrf.CsrfOptions;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * JAX-RS application for bootstrapping the Krazo controllers.
 */
@ApplicationPath(App.MVC_APP_ROOT)
public class App extends Application {

    public static final String MVC_APP_ROOT = "mvc";

    @Override
    public Map<String, Object> getProperties() {
        return Map.of(Csrf.CSRF_PROTECTION, CsrfOptions.IMPLICIT);
    }
}
