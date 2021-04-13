package de.erdlet.covistat;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * JAX-RS application for bootstrapping the Krazo controllers. Can run on the context root, because the mustache
 * extension won't forward to the servlet engine.
 */
@ApplicationPath("/")
public class App extends Application {
}
