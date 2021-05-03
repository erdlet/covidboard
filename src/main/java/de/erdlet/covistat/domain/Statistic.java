package de.erdlet.covistat.domain;

import java.time.Instant;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * A {@link Statistic} contains the daily COVID-19 information release by the Robert Koch Institut (RKI).
 *
 * Each of these records is applied to a {@link County}.
 *
 * @author erdlet
 *
 */
@Entity
@Table(name = "statistic")
public class Statistic {

    /**
     * The unique ID of each statistic.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The information about the seven day incidence per 100k people in a county.
     */
    @Column(name = "seven_day_incidence")
    private Double sevenDayIncidence;

    /**
     * Shows at which timestamp the data is delivered by the RKI.
     */
    @Column(name = "rki_timestamp")
    private Instant rkiTimestamp;

    /**
     * The {@link County} to which this {@link Statistic} belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private County county;

    public Statistic() {
    }

    public Statistic(final Double sevenDayIncidence, final Instant rkiTimestamp, final County county) {
        this.sevenDayIncidence = sevenDayIncidence;
        this.rkiTimestamp = rkiTimestamp;
        this.county = county;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Double getSevenDayIncidence() {
        return sevenDayIncidence;
    }

    public void setSevenDayIncidence(final Double sevenDayIncidence) {
        this.sevenDayIncidence = sevenDayIncidence;
    }

    public Instant getRkiTimestamp() {
        return rkiTimestamp;
    }

    public void setRkiTimestamp(final Instant rkiTimestamp) {
        this.rkiTimestamp = rkiTimestamp;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(final County county) {
        this.county = county;
    }

    @Override
    public int hashCode() {
        return Objects.hash(county, id, rkiTimestamp, sevenDayIncidence);
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
        final Statistic other = (Statistic) obj;
        return Objects.equals(county, other.county) && Objects.equals(id, other.id) && Objects.equals(rkiTimestamp, other.rkiTimestamp)
                && Objects.equals(sevenDayIncidence, other.sevenDayIncidence);
    }

    @Override
    public String toString() {
        return "Statistic [id=" + id + ", sevenDayIncidence=" + sevenDayIncidence + ", rkiTimestamp=" + rkiTimestamp + ", county=" + county + "]";
    }
}
