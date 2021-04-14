package de.erdlet.covistat.dataloader;

import javax.json.bind.annotation.JsonbProperty;

/**
 * The attributes of a ArcGis feature. Contains the values which are interesting for the application.
 *
 * @author erdlet
 *
 */
public class ArcGisFeatureAttributes {

    @JsonbProperty("AGS")
    public String ags;

    @JsonbProperty("county")
    public String county;

    @JsonbProperty("cases7_per_100k")
    public Double casesInSevenDaysPer100KPeople;

    @JsonbProperty("last_update")
    public String lastUpdate;

    @Override
    public String toString() {
        return "ArcGisFeatureAttributes [ags=" + ags + ", county=" + county + ", casesInSevenDaysPer100KPeople=" + casesInSevenDaysPer100KPeople
                + ", lastUpdate=" + lastUpdate + "]";
    }
}
