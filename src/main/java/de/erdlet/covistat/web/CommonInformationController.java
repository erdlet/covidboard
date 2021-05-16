package de.erdlet.covistat.web;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.UriRef;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("allgemeines-und-quellen")
@Controller
public class CommonInformationController {

    @Inject
    Models models;

    @Inject
    Router router;

    @GET
    @UriRef("common-info")
    public Response showCommonInfo() {
        models.put("router", router);
        return Response.ok("common-info.mustache").build();
    }
}
