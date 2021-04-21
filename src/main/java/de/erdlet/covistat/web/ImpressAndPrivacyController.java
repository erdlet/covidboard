package de.erdlet.covistat.web;
import javax.mvc.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("legal")
@Controller
public class ImpressAndPrivacyController {

    @GET
    @Path("impress")
    public Response showImpress() {
        return Response.ok("impress.mustache").build();
    }

    @GET
    @Path("privacy")
    public Response showPrivacyDeclaration() {
        return Response.ok("privacy.mustache").build();
    }
}
