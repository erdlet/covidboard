package de.erdlet.covidboard.web;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.UriRef;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.metrics.annotation.Counted;

import de.erdlet.covidboard.statistics.DatabaseStatisticsProvider;

@Path("")
@Controller
public class DashboardController {

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
    public String index() {
        models.put("page", new DashboardPage(statisticsProvider.loadLatestCountyStatistics()));
        models.put("linkToCountyDetails", router.linkToCountyDetailsFunc());
        models.put("router", router);
        return "index.peb";
    }
}
