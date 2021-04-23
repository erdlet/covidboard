package de.erdlet.covistat.web;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import de.erdlet.covistat.App;

/**
 * Because the MVC API can't be used as context root in an application, this filter has to redirect to the MVC
 * application's index URL.
 *
 * @author erdlet
 *
 */
@WebFilter(urlPatterns = "/")
public class IndexRedirector extends HttpFilter {

    private static final long serialVersionUID = 6306253889524826679L;

    private static final Logger LOGGER = Logger.getLogger(IndexRedirector.class.getName());

    @Override
    protected void doFilter(final HttpServletRequest req, final HttpServletResponse res, final FilterChain chain)
            throws IOException, ServletException {
        final String redirectTarget = req.getRequestURL() + App.MVC_APP_ROOT;

        LOGGER.info("Redirecting to: " + redirectTarget);

        res.sendRedirect(redirectTarget);
    }
}
