package de.erdlet.covistat.web;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.UriRef;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import de.erdlet.covistat.statistics.CachingStatisticsProvider;

@Path("")
@Controller
public class DashboardController {

    @Inject
    Models models;

    @Inject
    Router router;

    @Inject
    CachingStatisticsProvider statisticsProvider;

    @GET
    @UriRef("dashboard")
    public String index() {
        models.put("page", new DashboardPage(statisticsProvider.getRawStatisticData()));
        models.put("router", router);
        return "index.mustache";
    }
}
