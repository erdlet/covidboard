package de.erdlet.covidboard.domain;

import java.time.LocalDate;

/**
 * Contains the latest {@link Statistic} for a {@link County}. Is aggregated out of a {@link County} and a
 * {@link Statistic}.
 *
 * @author erdlet
 *
 */
public final class LatestCountyStatistic {

    public final String ags;
    public final String county;
    public final Double sevenDayIncidence;
    public final LocalDate lastUpdate;

    public LatestCountyStatistic(final County county, final Statistic statistic) {
        this(county.getAgs(), county.getName(), statistic.getSevenDayIncidence(), statistic.getRkiDate());
    }

    public LatestCountyStatistic(final String ags, final String county, final Double sevenDayIncidence, final LocalDate rkiDate) {
        this.ags = ags;
        this.county = county;
        this.sevenDayIncidence = sevenDayIncidence;
        this.lastUpdate = rkiDate;
    }

    @Override
    public String toString() {
        return "LatestCountyStatistic [ags=" + ags + ", county=" + county + ", sevenDayIncidence=" + sevenDayIncidence + ", lastUpdateInGermanFormat="
                + lastUpdate + "]";
    }
}
