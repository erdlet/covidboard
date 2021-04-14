package de.erdlet.covistat.web;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

import de.erdlet.covistat.dataloader.APIClient;

@Path("")
@Controller
public class DashboardController {

    @Inject
    Models models;

    @Inject
    APIClient arcGisClient;

    @GET
    public String index() {
        models.put("page", new DashboardPage(arcGisClient.fetchStatistics()));
        return "index.mustache";
    }
}
