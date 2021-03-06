package de.erdlet.covidboard.dataloader;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpTimeoutException;
import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;

import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * Client for accessing the REST API which provides the statistics.
 *
 * @author erdlet
 *
 */
@ApplicationScoped
public class APIClient {

    private static final Logger LOGGER = Logger.getLogger(APIClient.class.getName());

    @Inject
    @ConfigProperty(name = "api.uri")
    String apiUri;

    public List<APIResponseFeature> fetchStatistics() {

        try {
            final HttpRequest request = HttpRequest.newBuilder(URI.create(apiUri)).GET().build();
            final HttpClient client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();

            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            final APIResponse parsedBody = JsonbBuilder.create().fromJson(response.body(), APIResponse.class);

            return parsedBody.features;

        } catch (final HttpTimeoutException ex) {
            LOGGER.warning("API could not be accessed within 5 seconds. Returning empty result.");
            return List.of();
        } catch (final IOException e) {
            LOGGER.warning("IO error occured during process. Returning empty result.");
            return List.of();
        } catch (final InterruptedException e) {
            LOGGER.warning("Request was interrupted. Returning empty result.");
            return List.of();
        }
    }
}
