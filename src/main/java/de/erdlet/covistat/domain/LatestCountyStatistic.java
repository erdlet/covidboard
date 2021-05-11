package de.erdlet.covistat.domain;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Contains the latest {@link Statistic} for a {@link County}.
 *
 * @author erdlet
 *
 */
public final class LatestCountyStatistic {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public final String ags;
    public final String county;
    public final String sevenDayIncidence;
    public final String lastUpdateInGermanFormat;

    public LatestCountyStatistic(final String ags, final String county, final Double sevenDayIncidence, final LocalDate rkiDate) {
        this.ags = ags;
        this.county = county;
        this.sevenDayIncidence = String.format("%.2f", sevenDayIncidence);
        this.lastUpdateInGermanFormat = FORMATTER.format(rkiDate);
    }

    @Override
    public String toString() {
        return "LatestCountyStatistic [ags=" + ags + ", county=" + county + ", sevenDayIncidence=" + sevenDayIncidence + ", lastUpdateInGermanFormat="
                + lastUpdateInGermanFormat + "]";
    }
}
