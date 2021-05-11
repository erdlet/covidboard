package de.erdlet.covistat.dataloader;

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

    public LocalDate parseLastUpdateAsDate() {
        final String lastUpdateWithoutText = lastUpdate.replace(" Uhr", "").replace(",", "");
        return LocalDate.parse(lastUpdateWithoutText, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
    }

    @Override
    public String toString() {
        return "ArcGisFeatureAttributes [ags=" + ags + ", county=" + county + ", casesInSevenDaysPer100KPeople=" + casesInSevenDaysPer100KPeople
                + ", lastUpdate=" + lastUpdate + "]";
    }
}
