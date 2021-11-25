package de.erdlet.covidboard.dataloader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockserver.integration.ClientAndServer;
import org.mockserver.model.MediaType;

public class APIClientIT {

    /**
     * A random port number which hopefully isn't used anywhere else ;)
     */
    private static final int PORT = 42335;

    private static final String TEST_API_URI = String.format("http://localhost:%d", PORT);
    private static final String TEST_API_RESOURCE = "/statistics";

    private static ClientAndServer mockServer;

    private APIClient sut;

    @BeforeClass
    public static void setupMockServer() {
        mockServer = ClientAndServer.startClientAndServer(PORT);

        mockServer
        .when(
                request()
                .withMethod("GET")
                .withPath(TEST_API_RESOURCE))
        .respond(
                response()
                .withBody(resultJson(), MediaType.APPLICATION_JSON_UTF_8));
    }

    @Before
    public void setupSut() {
        sut = new APIClient();
        sut.apiUri = TEST_API_URI + TEST_API_RESOURCE;
    }

    @AfterClass
    public static void stopMockServer() {
        mockServer.stop();
    }

    @Test
    public void fetchStatistics_shouldReturnCorrectListSize() {

        final List<APIResponseFeature> actual = sut.fetchStatistics();

        assertThat(actual).hasSize(3);
    }

    @Test
    public void fetchStatistics_shouldMapValuesCorrect() {

        final List<APIResponseFeature> actual = sut.fetchStatistics();

        checkFeatureAttributeValues(actual.get(0).attributes, "SK Flensburg", "01001", 49.9090546115966, "14.04.2021, 00:00 Uhr");
        checkFeatureAttributeValues(actual.get(1).attributes, "SK Kiel", "01002", 85.4964059093819, "14.04.2021, 00:00 Uhr");
        checkFeatureAttributeValues(actual.get(2).attributes, "SK Lübeck", "01003", 73.8927631275112, "14.04.2021, 00:00 Uhr");

    }

    private static void checkFeatureAttributeValues(final APIResponseFeatureAttributes attributes, final String county, final String ags,
            final Double sevenDayIncidence, final String lastUpdate) {
        assertThat(attributes).hasFieldOrPropertyWithValue("county", county);
        assertThat(attributes).hasFieldOrPropertyWithValue("ags", ags);
        assertThat(attributes).hasFieldOrPropertyWithValue("casesInSevenDaysPer100KPeople", sevenDayIncidence);
        assertThat(attributes).hasFieldOrPropertyWithValue("lastUpdate", lastUpdate);
    }

    /**
     * This JSON contains three features which contain the interesting data.
     *
     * It contains:
     *
     * <ul>
     * <li>SK Flensburg | AGS: 01001 | Inzidenz: 49.9090546115966 | lastUpdate: 14.04.2021, 00:00 Uhr</li>
     * <li>SK Kiel | AGS: 01002 | Inzidenz: 85.4964059093819 | lastUpdate: 14.04.2021, 00:00 Uhr</li>
     * <li>SK Lübeck | AGS: 01003 | Inzidenz: 73.8927631275112 | lastUpdate: 14.04.2021, 00:00 Uhr</li>
     * <ul>
     *
     * The rest is metadata I don't care about.
     */
    private static final String resultJson() {
        return "{\n"
                + "  \"objectIdFieldName\": \"OBJECTID\",\n"
                + "  \"uniqueIdField\": {\n"
                + "    \"name\": \"OBJECTID\",\n"
                + "    \"isSystemMaintained\": true\n"
                + "  },\n"
                + "  \"globalIdFieldName\": \"\",\n"
                + "  \"geometryType\": \"esriGeometryPolygon\",\n"
                + "  \"spatialReference\": {\n"
                + "    \"wkid\": 4326,\n"
                + "    \"latestWkid\": 4326\n"
                + "  },\n"
                + "  \"fields\": [\n"
                + "    {\n"
                + "      \"name\": \"AGS\",\n"
                + "      \"type\": \"esriFieldTypeString\",\n"
                + "      \"alias\": \"AGS\",\n"
                + "      \"sqlType\": \"sqlTypeOther\",\n"
                + "      \"length\": 5,\n"
                + "      \"domain\": null,\n"
                + "      \"defaultValue\": null\n"
                + "    },\n"
                + "    {\n"
                + "      \"name\": \"county\",\n"
                + "      \"type\": \"esriFieldTypeString\",\n"
                + "      \"alias\": \"Landkreis\",\n"
                + "      \"sqlType\": \"sqlTypeOther\",\n"
                + "      \"length\": 256,\n"
                + "      \"domain\": null,\n"
                + "      \"defaultValue\": null\n"
                + "    },\n"
                + "    {\n"
                + "      \"name\": \"cases7_per_100k\",\n"
                + "      \"type\": \"esriFieldTypeDouble\",\n"
                + "      \"alias\": \"Fälle letzte 7 Tage/100.000 EW\",\n"
                + "      \"sqlType\": \"sqlTypeOther\",\n"
                + "      \"domain\": null,\n"
                + "      \"defaultValue\": null\n"
                + "    },\n"
                + "    {\n"
                + "      \"name\": \"BL\",\n"
                + "      \"type\": \"esriFieldTypeString\",\n"
                + "      \"alias\": \"Bundesland\",\n"
                + "      \"sqlType\": \"sqlTypeOther\",\n"
                + "      \"length\": 256,\n"
                + "      \"domain\": null,\n"
                + "      \"defaultValue\": null\n"
                + "    },\n"
                + "    {\n"
                + "      \"name\": \"last_update\",\n"
                + "      \"type\": \"esriFieldTypeString\",\n"
                + "      \"alias\": \"Aktualisierung\",\n"
                + "      \"sqlType\": \"sqlTypeOther\",\n"
                + "      \"length\": 256,\n"
                + "      \"domain\": null,\n"
                + "      \"defaultValue\": null\n"
                + "    }\n"
                + "  ],\n"
                + "  \"features\": [\n"
                + "    {\n"
                + "      \"attributes\": {\n"
                + "        \"AGS\": \"01001\",\n"
                + "        \"county\": \"SK Flensburg\",\n"
                + "        \"cases7_per_100k\": 49.9090546115966,\n"
                + "        \"BL\": \"Schleswig-Holstein\",\n"
                + "        \"last_update\": \"14.04.2021, 00:00 Uhr\"\n"
                + "      }\n"
                + "    },\n"
                + "    {\n"
                + "      \"attributes\": {\n"
                + "        \"AGS\": \"01002\",\n"
                + "        \"county\": \"SK Kiel\",\n"
                + "        \"cases7_per_100k\": 85.4964059093819,\n"
                + "        \"BL\": \"Schleswig-Holstein\",\n"
                + "        \"last_update\": \"14.04.2021, 00:00 Uhr\"\n"
                + "      }\n"
                + "    },\n"
                + "    {\n"
                + "      \"attributes\": {\n"
                + "        \"AGS\": \"01003\",\n"
                + "        \"county\": \"SK Lübeck\",\n"
                + "        \"cases7_per_100k\": 73.8927631275112,\n"
                + "        \"BL\": \"Schleswig-Holstein\",\n"
                + "        \"last_update\": \"14.04.2021, 00:00 Uhr\"\n"
                + "      }\n"
                + "    }\n"
                + "  ]\n"
                + "}";
    }
}
