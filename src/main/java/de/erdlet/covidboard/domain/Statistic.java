package de.erdlet.covidboard.domain;

import java.time.LocalDate;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * A {@link Statistic} contains the daily COVID-19 information release by the Robert Koch Institut (RKI).
 *
 * Each of these records is applied to a {@link County}. {@link Statistic}s are only written and never deleted.
 *
 * @author erdlet
 *
 */
@Entity
@Table(name = "statistics")
@NamedQueries({
    @NamedQuery(name = Statistic.FIND_LATEST_STATISTICS, query = "SELECT new de.erdlet.covidboard.domain.LatestCountyStatistic("
            + "county.ags, county.name, stat.sevenDayIncidence, stat.rkiDate) "
            + " FROM Statistic stat JOIN stat.county county WHERE stat.rkiDate = (SELECT max(stat2.rkiDate) FROM Statistic stat2 WHERE stat2.county = county)"
            + " ORDER BY county.ags"),
    @NamedQuery(name = Statistic.FIND_FOR_DATE, query = "SELECT stat.id FROM Statistic stat WHERE rkiDate = :rkiDate AND county = :county"),
    @NamedQuery(name = Statistic.FIND_LATEST_STATISTICS_FOR_FILTER, query = "SELECT"
            + " new de.erdlet.covidboard.domain.LatestCountyStatistic(county.ags, county.name, stat.sevenDayIncidence, stat.rkiDate)"
            + " FROM Statistic stat JOIN stat.county county WHERE stat.rkiDate = (SELECT max(stat2.rkiDate) FROM Statistic stat2 WHERE stat2.county = county)"
            + " AND (county.ags LIKE :filterQuery OR county.name LIKE :filterQuery)"
            + " ORDER BY county.ags"),
    @NamedQuery(name = Statistic.FIND_LATEST_STATISTICS_FOR_FILTER_WITHOUT_FAVORITES, query = "SELECT"
            + " new de.erdlet.covidboard.domain.LatestCountyStatistic(county.ags, county.name, stat.sevenDayIncidence, stat.rkiDate)"
            + " FROM Statistic stat JOIN stat.county county WHERE stat.rkiDate = (SELECT max(stat2.rkiDate) FROM Statistic stat2 WHERE stat2.county = county)"
            + " AND (county.ags LIKE :filterQuery OR county.name LIKE :filterQuery)"
            + " AND county.ags NOT IN (:favorites)"
            + " ORDER BY county.ags"),
    @NamedQuery(name = Statistic.FIND_STATISTICS_FOR_AGS, query = "SELECT"
            + " new de.erdlet.covidboard.domain.LatestCountyStatistic(county.ags, county.name, stat.sevenDayIncidence, stat.rkiDate)"
            + " FROM Statistic stat JOIN stat.county county WHERE stat.rkiDate = (SELECT max(stat2.rkiDate) FROM Statistic stat2 WHERE stat2.county = county)"
            + " AND county.ags IN :agsList"
            + " ORDER BY county.ags")
})
public class Statistic {

    public static final String FIND_LATEST_STATISTICS = "Statistic.findLatestStatisticsForCounties";
    public static final String FIND_FOR_DATE = "Statistic.findForDate";
    public static final String FIND_LATEST_STATISTICS_FOR_FILTER = "Statistic.findLatestStatisticsForCountyFilter";
    public static final String FIND_LATEST_STATISTICS_FOR_FILTER_WITHOUT_FAVORITES = "Statistic.findLatestStatisticsForCountyFilterWithoutFavorites";
    public static final String FIND_STATISTICS_FOR_AGS = "Statistic.findStatisticsForAgs";

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
     * Shows at which date the data is delivered by the RKI.
     */
    @Column(name = "rki_date")
    private LocalDate rkiDate;

    /**
     * The {@link County} to which this {@link Statistic} belongs.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    private County county;

    public Statistic() {
    }

    public Statistic(final Double sevenDayIncidence, final LocalDate rkiDate, final County county) {
        this.sevenDayIncidence = sevenDayIncidence;
        this.rkiDate = rkiDate;
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

    public LocalDate getRkiDate() {
        return rkiDate;
    }

    public void setRkiDate(final LocalDate rkiDate) {
        this.rkiDate = rkiDate;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(final County county) {
        this.county = county;
    }

    @Override
    public int hashCode() {
        return Objects.hash(county, id, rkiDate, sevenDayIncidence);
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
        return Objects.equals(county, other.county) && Objects.equals(id, other.id) && Objects.equals(rkiDate, other.rkiDate)
                && Objects.equals(sevenDayIncidence, other.sevenDayIncidence);
    }

    @Override
    public String toString() {
        return "Statistic [id=" + id + ", sevenDayIncidence=" + sevenDayIncidence + ", rkiDate=" + rkiDate + "]";
    }
}
