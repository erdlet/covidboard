package de.erdlet.covistat.domain;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Contains Corona statistics for a specific county. A county is identified by an
 *
 * @author erdlet
 *
 */
@Entity
@Table(name = "county_statistics")
public class CountyStatistic implements DomainObject<CountyStatistic> {

    @Embedded
    private CountyId id;

    private String ags;

    private String county;

    private Double sevenDayIncidence;

    private Instant reportTimestamp;

    public CountyStatistic() {
    }

    public  CountyStatistic(final String ags, final String county, final Double sevenDayIncidence, final String reportTimestamp) {
        this.id = new CountyId(ags);
        this.ags = ags;
        this.county = county;
        this.sevenDayIncidence = sevenDayIncidence;
        this.reportTimestamp = parseTimestamp(reportTimestamp);
    }

    /**
     * Parses the text timestamp to an {@link Instant}. This enables the statistics to be sortable.
     *
     * @param reportTimestamp the original report timestamp in the format "dd.MM.yyyy, HH:mm Uhr"
     * @return the Instant of the original timestamp
     */
    private Instant parseTimestamp(final String reportTimestamp) {
        final String reportTimestampWithoutText = reportTimestamp.replace(" Uhr", "").replace(",", "");
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

        return LocalDateTime.parse(reportTimestampWithoutText, formatter).atZone(Defaults.GERMAN_TIMEZONE).toInstant();

    }

    @Override
    public boolean isSameAs(final CountyStatistic other) {
        return false;
    }

    public CountyId getId() {
        return id;
    }

    public void setId(final CountyId id) {
        this.id = id;
    }

    public String getAgs() {
        return ags;
    }

    public void setAgs(final String ags) {
        this.ags = ags;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(final String county) {
        this.county = county;
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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (ags == null ? 0 : ags.hashCode());
        result = prime * result + (county == null ? 0 : county.hashCode());
        result = prime * result + (id == null ? 0 : id.hashCode());
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
        if (county == null) {
            if (other.county != null) {
                return false;
            }
        } else if (!county.equals(other.county)) {
            return false;
        }
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
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
        return "CountyStatistic [id=" + id + ", ags=" + ags + ", county=" + county + ", sevenDayIncidence=" + sevenDayIncidence + ", reportTimestamp="
                + reportTimestamp + "]";
    }
}
