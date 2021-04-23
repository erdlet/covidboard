package de.erdlet.covistat.web;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mvc.MvcContext;

/**
 * The router constructs the internal routing and provides the URIs for the views.
 *
 * @author erdlet
 *
 */
@RequestScoped
public class Router {

    @Inject
    private MvcContext mvcContext;

    public String linkToDashboard() {
        return mvcContext.uri("dashboard").toASCIIString();
    }

    public String linkToImpress() {
        return mvcContext.uri("impress").toASCIIString();
    }

    public String linkToPrivacy() {
        return mvcContext.uri("privacy").toASCIIString();
    }

}
