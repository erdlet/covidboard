package de.erdlet.covidboard;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * JAX-RS application for bootstrapping the Krazo controllers. Can run on the context root, because the mustache
 * extension won't forward to the servlet engine.
 */
@ApplicationPath(App.MVC_APP_ROOT)
public class App extends Application {

    public static final String MVC_APP_ROOT = "mvc";
}
