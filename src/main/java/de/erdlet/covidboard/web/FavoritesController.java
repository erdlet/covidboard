package de.erdlet.covidboard.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.UriRef;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.metrics.annotation.Counted;

import de.erdlet.covidboard.web.cookie.CookieFactory;
import de.erdlet.covidboard.web.cookie.FavoritesCookie;

@Path("favorites")
@Controller
public class FavoritesController {

    @Inject
    Router router;

    @Inject
    CookieFactory cookieFactory;

    @POST
    @UriRef("add-favorite")
    @Counted(name = "favorites_added", absolute = true)
    public Response addCountyToFavorites(@FormParam("ags") final String ags, @QueryParam("filter") final String filter,
            @CookieParam(Cookies.CB_FAVORITES) final Cookie favoritesCookie) {

        if (ags == null || ags.isEmpty()) {
            return Response.status(Status.SEE_OTHER).entity(router.redirectToSearch(filter)).build();
        }

        if (favoritesCookie == null) {
            final FavoritesCookie freshFavoritesCookie = cookieFactory.createForValue(ags);
            return Response.status(Status.SEE_OTHER).entity(router.redirectToSearch(filter)).cookie(freshFavoritesCookie).build();
        }

        final List<String> favoritesList = extractFavoritesAsList(favoritesCookie.getValue());

        if (!favoritesList.contains(ags)) {
            favoritesList.add(ags);
        }

        final String updatedFavorites = formatUpdatedValue(favoritesList);
        final FavoritesCookie updatedCookie = cookieFactory.createForValue(updatedFavorites);

        return Response.status(Status.SEE_OTHER).cookie(updatedCookie).entity(router.redirectToSearch(filter)).build();
    }

    @DELETE
    @Path("{ags}")
    @UriRef("remove-favorite")
    @Counted(name = "favorites_removed", absolute = true)
    public Response deleteFavorite(@PathParam("ags") final String ags,
            @CookieParam(Cookies.CB_FAVORITES) final Cookie favoritesCookie) {
        if (isAgsNullOrEmpty(ags) || isFavoritesCookieNullOrEmpty(favoritesCookie)) {
            return Response.status(Status.SEE_OTHER).entity(router.redirectToDashboard()).build();
        }

        final List<String> favorites = extractFavoritesAsList(favoritesCookie.getValue());

        if (favorites.contains(ags)) {
            favorites.remove(ags);
        }

        final String updatedFavorites = formatUpdatedValue(favorites);
        final FavoritesCookie updatedCookie = cookieFactory.createForValue(updatedFavorites);
        return Response.status(Status.SEE_OTHER).cookie(updatedCookie).entity(router.redirectToDashboard()).build();
    }

    private boolean isAgsNullOrEmpty(final String ags) {
        return ags == null || ags.isBlank();
    }

    private boolean isFavoritesCookieNullOrEmpty(final Cookie cookie) {
        return cookie == null || cookie.getValue() == null || cookie.getValue().isBlank();
    }

    List<String> extractFavoritesAsList(final String value) {
        return Stream.of(value.split("&")).collect(Collectors.toList());
    }

    String formatUpdatedValue(final List<String> favorites) {
        return favorites.stream().collect(Collectors.joining("&"));
    }
}
