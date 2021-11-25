package de.erdlet.covidboard.dataloader;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.json.bind.annotation.JsonbProperty;

/**
 * The attributes of a {@link APIResponseFeature}. Contains the values which are interesting for the application.
 *
 * @author erdlet
 *
 */
public class APIResponseFeatureAttributes {

    @JsonbProperty("AGS")
    public String ags;

    @JsonbProperty("county")
    public String county;

    @JsonbProperty("cases7_per_100k")
    public Double casesInSevenDaysPer100KPeople;

    @JsonbProperty("last_update")
    public String lastUpdate;

    /**
     * Parses the timestamp delivered by the API (e.g. 14.04.2021, 00:00 Uhr) into a {@link LocalDate}. Because the
     * timestamp is everytime set to 00:00, it will be discarded.
     *
     * @return the {@link LocalDate} extracted from the timestamp
     */
    public LocalDate parseLastUpdateAsDate() {
        if (lastUpdate == null || lastUpdate.isBlank()) {
            return null;
        }

        final String lastUpdateWithoutText = lastUpdate.replace(" Uhr", "").replace(",", "");
        return LocalDate.parse(lastUpdateWithoutText, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return "APIResponseFeatureAttributes [ags=" + ags + ", county=" + county + ", casesInSevenDaysPer100KPeople=" + casesInSevenDaysPer100KPeople
                + ", lastUpdate=" + lastUpdate + "]";
    }
}
