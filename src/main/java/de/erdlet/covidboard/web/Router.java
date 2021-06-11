package de.erdlet.covidboard.web;

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

    public String redirectToDashboard(final String filter) {
        final String uri = "redirect:" + linkToDashboard();

        return filter == null || filter.isBlank() ? uri : uri + "?filter=" + filter;
    }

    public String linkToCommonInformation() {
        return mvcContext.uri("common-info").toASCIIString();
    }

    public String linkToImpress() {
        return mvcContext.uri("impress").toASCIIString();
    }

    public String linkToPrivacy() {
        return mvcContext.uri("privacy").toASCIIString();
    }

    public String linkToCountyDetails(final String ags) {
        return mvcContext.uriBuilder("countyDetails").build(ags).toASCIIString();
    }

    public String linkForAddingFavorite() {
        return mvcContext.uri("add-favorite").toASCIIString();
    }

    public String linkForRemovingFavorite(final String ags) {
        return mvcContext.uriBuilder("remove-favorite").build(ags).toASCIIString();
    }
}
