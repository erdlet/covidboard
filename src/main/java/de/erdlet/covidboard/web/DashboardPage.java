package de.erdlet.covidboard.web;

import java.util.List;
import java.util.stream.Collectors;

import de.erdlet.covidboard.domain.LatestCountyStatistic;

public final class DashboardPage {

    public final List<CardContent> content;
    public final List<CardContent> favorites;

    public DashboardPage(final List<LatestCountyStatistic> latestStatistics, final List<LatestCountyStatistic> favorites) {
        // TODO: Favoriten rausfiltern
        this.content = latestStatistics.stream().map(CardContent::new).collect(Collectors.toUnmodifiableList());
        this.favorites = favorites.stream().map(CardContent::new).collect(Collectors.toUnmodifiableList());
    }

    // TODO: Attribut für Favorit ja / nein
    public static class CardContent {
        public final String countyName;
        public final String ags;
        public final String sevenDayIncidence;
        public final String lastUpdate;

        public CardContent(final LatestCountyStatistic statistic) {
            this.countyName = statistic.county;
            this.ags = statistic.ags;
            this.sevenDayIncidence = Formatters.formatDoubleToStringWithTwoDecimalAfterComma(statistic.sevenDayIncidence);
            this.lastUpdate = Formatters.formatDateToGermanLayout(statistic.lastUpdate);
        }
    }
}
