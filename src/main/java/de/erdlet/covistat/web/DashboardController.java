package de.erdlet.covistat.web;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.UriRef;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import de.erdlet.covistat.statistics.DatabaseStatisticsProvider;

@Path("")
@Controller
public class DashboardController {

    @Inject
    Models models;

    @Inject
    Router router;

    @Inject
    DatabaseStatisticsProvider statisticsProvider;

    @GET
    @UriRef("dashboard")
    @Transactional
    public String index() {
        models.put("statistics", statisticsProvider.loadLatestCountyStatistics());
        models.put("router", router);
        return "index.mustache";
    }
}
