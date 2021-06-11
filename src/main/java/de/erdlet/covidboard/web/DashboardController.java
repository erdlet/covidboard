package de.erdlet.covidboard.web;

import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.UriRef;
import javax.transaction.Transactional;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;

import org.eclipse.microprofile.metrics.annotation.Counted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.erdlet.covidboard.domain.LatestCountyStatistic;
import de.erdlet.covidboard.statistics.DatabaseStatisticsProvider;

@Path("")
@Controller
public class DashboardController {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardController.class);

    @Inject
    Models models;

    @Inject
    Router router;

    @Inject
    Formatters formatters;

    @Inject
    DatabaseStatisticsProvider statisticsProvider;

    @GET
    @UriRef("dashboard")
    @Transactional
    @Counted(name = "dashboard_views", absolute = true)
    public String index(@QueryParam("filter") @DefaultValue("") final String filter, @CookieParam(Cookies.CB_FAVORITES) final Cookie favoriteCookie) {

        final List<String> favorites = extractFavoritesFromCookie(favoriteCookie);

        LOGGER.debug("Request contains favorites Cookie with content: {}", favorites);

        models.put("page", new DashboardPage(fetchStatisticsBasedOnFilter(filter), statisticsProvider.loadLatestCountyStatisticsForAgsList(favorites)));
        models.put("filter", isFilterSet(filter) ? filter : null);
        models.put("router", router);
        return "index.peb";
    }

    private List<String> extractFavoritesFromCookie(final Cookie favoriteCookie) {
        return favoriteCookie == null ? List.of() : Arrays.asList(favoriteCookie.getValue().split(","));
    }

    private List<LatestCountyStatistic> fetchStatisticsBasedOnFilter(final String filter) {
        switch (filter) {
        case "all":
            return statisticsProvider.loadLatestCountyStatistics();
        case "":
            return List.of();
        default:
            return statisticsProvider.loadLatestCountyStatisticsForFilter(filter);
        }
    }

    private boolean isFilterSet(final String filter) {
        return filter != null && !filter.isBlank();
    }
}
