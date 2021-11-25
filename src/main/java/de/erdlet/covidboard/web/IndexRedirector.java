package de.erdlet.covidboard.web;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.erdlet.covidboard.App;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(IndexRedirector.class);

    private static final String FORWARDED_FOR_SERVER_HEADER = "X-Forwarded-Server";

    @Override
    protected void doFilter(final HttpServletRequest req, final HttpServletResponse res, final FilterChain chain)
            throws IOException, ServletException {

        final Optional<String> header = Optional.ofNullable(req.getHeader(FORWARDED_FOR_SERVER_HEADER));

        if (header.isPresent()) {
            sendHttpsRedirect(header.get(), res);
        } else {
            sendLocalRedirect(res);
        }
    }

    private static final void sendHttpsRedirect(final String host, final HttpServletResponse res) throws IOException {
        final String uri = String.format("https://%s/%s", host, App.MVC_APP_ROOT);
        LOGGER.info("Redirecting to: " + uri);

        res.sendRedirect(uri);
    }

    private static final void sendLocalRedirect(final HttpServletResponse res) throws IOException {
        final String uri = "http://localhost:8080/" + App.MVC_APP_ROOT;

        LOGGER.info("Redirecting to: " + uri);

        res.sendRedirect(uri);
    }
}
