package de.erdlet.covistat.domain;

import java.util.List;

/**
 * Aggregated object to provide detailed information for a specific {@link County}.
 *
 * Contains e.g. a history of incidences.
 *
 * @author erdlet
 *
 */
public final class CountyDetails {

    public final String ags;
    public final String name;
    public final Statistic latestStatistic;
    public final List<Statistic> statisticsOfLastFourteenDays;

    public CountyDetails(final County county, final Statistic latestStatistic, final List<Statistic> statisticsOfLastFourteenDays) {
        this.ags = county.getAgs();
        this.name = county.getName();
        this.latestStatistic = latestStatistic;
        this.statisticsOfLastFourteenDays = statisticsOfLastFourteenDays;
    }

    @Override
    public String toString() {
        return "CountyDetails [ags=" + ags + ", name=" + name + ", latestStatistic=" + latestStatistic + ", statisticsOfLastFourteenDays="
                + statisticsOfLastFourteenDays + "]";
    }
}
