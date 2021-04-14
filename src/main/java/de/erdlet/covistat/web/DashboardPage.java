package de.erdlet.covistat.web;

import java.util.List;
import java.util.stream.Collectors;

import de.erdlet.covistat.dataloader.APIResponseFeature;
import de.erdlet.covistat.dataloader.APIResponseFeatureAttributes;

public class DashboardPage {

    public List<Entry> entries;

    public DashboardPage(final List<APIResponseFeature> features) {
        this.entries = features.stream().map(feature -> feature.attributes).map(Entry::new).collect(Collectors.toList());
    }

    public static class Entry {

        public final String ags;
        public final String county;
        public final String sevenDayIncidence;
        public final String lastUpdate;

        public Entry(final APIResponseFeatureAttributes attrs) {
            this.ags = attrs.ags;
            this.county = attrs.county;
            this.sevenDayIncidence = String.format("%.2f", attrs.casesInSevenDaysPer100KPeople);
            this.lastUpdate = attrs.lastUpdate;
        }
    }
}
