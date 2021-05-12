package de.erdlet.covistat.web;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.UriRef;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import de.erdlet.covistat.domain.County;
import de.erdlet.covistat.domain.CountyDetails;
import de.erdlet.covistat.statistics.CountyDetailsProvider;

/**
 * A {@link Controller} which provides routes related to {@link County}s.
 *
 * @author erdlet
 *
 */
@Controller
@Path("counties")
public class CountyController {

    @Inject
    Router router;

    @Inject
    Models models;

    @Inject
    CountyDetailsProvider countyDetailsProvider;

    @Inject
    Formatters formatters;

    @GET
    @Path("{ags}")
    @UriRef("countyDetails")
    public Response showCountyDetails(@PathParam("ags") final String ags) {
        return countyDetailsProvider.findCountyDetailsForAgs(ags)
                .map(this::handleSuccessfulRequest)
                .orElseGet(() -> handleUnknownCounty(ags));
    }

    private Response handleSuccessfulRequest(final CountyDetails countyDetails) {
        models.put("page", new DetailsPage(countyDetails));
        models.put("router", router);
        return Response.ok("details.mustache").build();
    }

    private Response handleUnknownCounty(final String ags) {
        models.put("router", router);
        return Response.status(Response.Status.NOT_FOUND).entity("404.mustache").build();
    }
}
