package de.erdlet.covidboard.web;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.mvc.Controller;
import javax.mvc.UriRef;
import javax.websocket.server.PathParam;
import javax.ws.rs.CookieParam;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import de.erdlet.covidboard.web.cookie.FavoritesCookie;

@Path("favorites")
@Controller
public class FavoritesController {

    @Inject
    Router router;

    @POST
    @UriRef("add-favorite")
    public Response addCountyToFavorites(@FormParam("ags") final String ags, @QueryParam("filter") final String filter,
            @CookieParam(Cookies.CB_FAVORITES) final Cookie favoritesCookie) {
        if (ags == null || ags.isEmpty()) {
            return Response.status(Status.SEE_OTHER).entity(router.redirectToDashboard(filter)).build();
        }

        if (favoritesCookie == null) {
            final NewCookie freshFavoritesCookie = new FavoritesCookie(ags);
            return Response.status(Status.SEE_OTHER).entity(router.redirectToDashboard(filter)).cookie(freshFavoritesCookie).build();
        }

        final List<String> favoritesList = extractFavoritesAsList(favoritesCookie);

        if (!favoritesList.contains(ags)) {
            favoritesList.add(ags);
        }

        final String updatedFavorites = formatUpdatedValue(favoritesList);
        final FavoritesCookie updatedCookie = new FavoritesCookie(updatedFavorites);

        return Response.status(Status.SEE_OTHER).cookie(updatedCookie).entity(router.redirectToDashboard(filter)).build();
    }

    @DELETE
    @Path("{ags}")
    @UriRef("remove-favorite")
    public Response deleteFavorite(@PathParam("ags") final String ags, @QueryParam("filter") final String filter,
            @CookieParam(Cookies.CB_FAVORITES) final Cookie favoritesCookie) {
        if (isAgsNullOrEmpty(ags) || isFavoritesCookieNullOrEmpty(favoritesCookie)) {
            return Response.status(Status.SEE_OTHER).entity(router.redirectToDashboard(filter)).build();
        }

        final List<String> favorites = extractFavoritesAsList(favoritesCookie);

        if (favorites.contains(ags)) {
            favorites.remove(ags);
        }

        final String updatedFavorites = formatUpdatedValue(favorites);
        final FavoritesCookie updatedCookie = new FavoritesCookie(updatedFavorites);

        return Response.status(Status.SEE_OTHER).cookie(updatedCookie).entity(router.redirectToDashboard(filter)).build();
    }

    private boolean isAgsNullOrEmpty(final String ags) {
        return ags == null || ags.isBlank();
    }

    private boolean isFavoritesCookieNullOrEmpty(final Cookie cookie) {
        return cookie == null || cookie.getValue() == null || cookie.getValue().isBlank();
    }

    private List<String> extractFavoritesAsList(final Cookie cookie) {
        return Stream.of(cookie.getValue().split(",")).collect(Collectors.toList());
    }

    private String formatUpdatedValue(final List<String> favorites) {
        return favorites.stream().collect(Collectors.joining(","));
    }
}
