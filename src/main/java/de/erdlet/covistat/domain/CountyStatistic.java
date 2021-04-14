package de.erdlet.covistat.domain;

import java.time.Instant;
import java.time.LocalDateTime;

/**
 * The {@link CountyStatistic} contains information about the Corona infections and other related information per
 * county. A county is identified by the german "Allgemeiner Gemeinde Schluessel", short AGS.
 *
 * @author erdlet
 *
 */
public class CountyStatistic {

    /**
     * The "Allgmeine Gemeinde Schluessel" for the county for which information is gathered.
     */
    private String ags;

    /**
     * The human readable name of the county;
     */
    private String countyName;

    /**
     * The Corona infection incidence per 100k people.
     */
    private Double sevenDayIncidence;

    /**
     * The timestamp on which the data is reported by the RKI. Stored as {@link Instant} to be timezone agnostic.
     */
    private Instant reportTimestamp;

    /**
     * Default constructor for frameworks.
     */
    public CountyStatistic() {

    }

    /**
     * Constructor for manual construction of a {@link CountyStatistic}.
     *
     * @param ags see {@link #ags}
     * @param countyName see {@link #countyName}
     * @param sevenDayIncidence see {@link #sevenDayIncidence}
     * @param reportTimestamp the {@link #reportTimestamp} as {@link LocalDateTime}
     */
    public CountyStatistic(final String ags, final String countyName, final Double sevenDayIncidence, final LocalDateTime reportTimestamp) {
        this.ags = ags;
        this.countyName = countyName;
        this.sevenDayIncidence = sevenDayIncidence;
        this.reportTimestamp = reportTimestamp.atZone(Defaults.EUROPE_BERLIN).toInstant();
    }

    public String getAgs() {
        return ags;
    }

    public void setAgs(final String ags) {
        this.ags = ags;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(final String countyName) {
        this.countyName = countyName;
    }

    public Double getSevenDayIncidence() {
        return sevenDayIncidence;
    }

    public void setSevenDayIncidence(final Double sevenDayIncidence) {
        this.sevenDayIncidence = sevenDayIncidence;
    }

    public Instant getReportTimestamp() {
        return reportTimestamp;
    }

    public void setReportTimestamp(final Instant reportTimestamp) {
        this.reportTimestamp = reportTimestamp;
    }

    public LocalDateTime getReportTimestampAsDateTime() {
        return LocalDateTime.ofInstant(reportTimestamp, Defaults.EUROPE_BERLIN);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (ags == null ? 0 : ags.hashCode());
        result = prime * result + (countyName == null ? 0 : countyName.hashCode());
        result = prime * result + (reportTimestamp == null ? 0 : reportTimestamp.hashCode());
        result = prime * result + (sevenDayIncidence == null ? 0 : sevenDayIncidence.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CountyStatistic other = (CountyStatistic) obj;
        if (ags == null) {
            if (other.ags != null) {
                return false;
            }
        } else if (!ags.equals(other.ags)) {
            return false;
        }
        if (countyName == null) {
            if (other.countyName != null) {
                return false;
            }
        } else if (!countyName.equals(other.countyName)) {
            return false;
        }
        if (reportTimestamp == null) {
            if (other.reportTimestamp != null) {
                return false;
            }
        } else if (!reportTimestamp.equals(other.reportTimestamp)) {
            return false;
        }
        if (sevenDayIncidence == null) {
            if (other.sevenDayIncidence != null) {
                return false;
            }
        } else if (!sevenDayIncidence.equals(other.sevenDayIncidence)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "CountyStatistic [ags=" + ags + ", countyName=" + countyName + ", sevenDayIncidence=" + sevenDayIncidence + ", reportTimestamp="
                + reportTimestamp + "]";
    }
}
