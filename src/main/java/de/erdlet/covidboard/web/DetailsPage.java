package de.erdlet.covidboard.web;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import de.erdlet.covidboard.domain.CountyDetails;
import de.erdlet.covidboard.domain.Statistic;

public class DetailsPage {

    public final String countyName;
    public final CurrentDataCardContent currentDataContent;
    public final List<HistoryCardContentEntry> historyCardContentEntries;

    public DetailsPage(final CountyDetails details) {
        this.countyName = details.name;
        this.currentDataContent = new CurrentDataCardContent(details.latestStatistic);
        this.historyCardContentEntries = details.statisticsOfLastFourteenDays.stream()
                .sorted(Comparator.comparing(Statistic::getRkiDate).reversed())
                .map(HistoryCardContentEntry::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public static class CurrentDataCardContent {
        public final String sevenDayIncidence;
        public final String lastUpdate;

        public CurrentDataCardContent(final Statistic statistic) {
            this.sevenDayIncidence = Formatters.formatDoubleToStringWithTwoDecimalAfterComma(statistic.getSevenDayIncidence());
            this.lastUpdate = Formatters.formatDateToGermanLayout(statistic.getRkiDate());
        }

        @Override
        public String toString() {
            return "CurrentDataCardContent [sevenDayIncidence=" + sevenDayIncidence + ", lastUpdate=" + lastUpdate + "]";
        }
    }

    public static class HistoryCardContentEntry {
        public final String sevenDayIncidence;
        public final String lastUpdate;

        public HistoryCardContentEntry(final Statistic statistic) {
            this.sevenDayIncidence = Formatters.formatDoubleToStringWithTwoDecimalAfterComma(statistic.getSevenDayIncidence());
            this.lastUpdate = Formatters.formatDateToGermanLayout(statistic.getRkiDate());
        }

        @Override
        public String toString() {
            return "HistoryCardContentEntry [sevenDayIncidence=" + sevenDayIncidence + ", lastUpdate=" + lastUpdate + "]";
        }
    }

    @Override
    public String toString() {
        return "DetailsPage [countyName=" + countyName + ", currentDataContent=" + currentDataContent + ", historyCardContentEntries="
                + historyCardContentEntries + "]";
    }
}
