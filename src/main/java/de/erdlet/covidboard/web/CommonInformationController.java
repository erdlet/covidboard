package de.erdlet.covidboard.web;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.UriRef;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.eclipse.microprofile.metrics.annotation.Counted;

@Path("help-and-source-notes")
@Controller
public class CommonInformationController {

    @Inject
    Models models;

    @Inject
    Router router;

    @GET
    @UriRef("common-info")
    @Counted(name = "common_info_views", absolute = true)
    public Response showCommonInfo() {
        models.put("router", router);
        return Response.ok("common-info.peb").build();
    }
}
