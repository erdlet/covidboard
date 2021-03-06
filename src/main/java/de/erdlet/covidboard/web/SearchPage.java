package de.erdlet.covidboard.web;

import java.util.List;
import java.util.stream.Collectors;

import de.erdlet.covidboard.domain.LatestCountyStatistic;

public final class SearchPage {

    public final List<CardContent> content;

    public SearchPage(final List<LatestCountyStatistic> latestStatistics) {
        this.content = latestStatistics.stream().map(CardContent::new).collect(Collectors.toUnmodifiableList());
    }

    public static class CardContent {
        public final String countyName;
        public final String ags;
        public final String sevenDayIncidence;
        public final String lastUpdate;

        public CardContent(final LatestCountyStatistic statistic) {
            this.countyName = statistic.county;
            this.ags = statistic.ags;
            this.sevenDayIncidence = Formatters.formatDoubleToStringWithTwoDecimalPlaces(statistic.sevenDayIncidence);
            this.lastUpdate = Formatters.formatDateToGermanLayout(statistic.lastUpdate);
        }
    }
}
