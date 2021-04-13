package de.erdlet.covistat.controller;

import javax.mvc.Controller;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("")
@Controller
public class AppController {

    @GET
    public String sayHello() {
        return "hello.mustache";
    }
}
