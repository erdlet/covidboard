package de.erdlet.covidboard.web;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.Models;
import javax.mvc.UriRef;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("legal")
@Controller
public class ImpressAndPrivacyController {

    @Inject
    Models models;

    @Inject
    Router router;

    @GET
    @Path("impress")
    @UriRef("impress")
    public Response showImpress() {
        models.put("router", router);
        return Response.ok("impress.peb").build();
    }

    @GET
    @Path("privacy")
    @UriRef("privacy")
    public Response showPrivacyDeclaration() {
        models.put("router", router);
        return Response.ok("privacy.peb").build();
    }
}
