package de.erdlet.covidboard.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * A {@link County} is a "kreisfreie Stadt" or a "Landkreis" for which COVID-19 information is gathered by the RKI.
 *
 * Each {@link County} is uniquely identified by the "AGS", which means "Allgemeiner Gemeinde Schluessel". To each
 * {@link County}, a list of {@link Statistic}s is maintained. These contain data like the seven day incidence. They can
 * only be added and never removed.
 *
 * @author erdlet
 * @see Statistic
 */
@Entity
@Table(name = "counties")
@NamedQueries({
    @NamedQuery(name = County.FIND_ALL, query = "SELECT c FROM County c"),
    @NamedQuery(name = County.FIND_ALL_ORDERED_BY_AGS, query = "SELECT c FROM County c ORDER BY ags"),
    @NamedQuery(name = County.FIND_BY_AGS_WITH_STATISTICS, query = "SELECT c FROM County c JOIN FETCH c.statistics WHERE ags = :ags")
})
public class County {

    public static final String FIND_ALL = "County.findAll";
    public static final String FIND_ALL_ORDERED_BY_AGS = "County.findAllOrderedByAgs";
    public static final String FIND_BY_AGS_WITH_STATISTICS = "County.findByAgsWithStatistics";

    private static final int STATISTIC_HISTORY_DAYS = 14;

    @Id
    private String ags;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "county_ags")
    private List<Statistic> statistics;

    public County() {
    }

    /**
     * Constructor for creating a new {@link County}.
     *
     * @param ags the "Allgemeiner Gemeinde Schluessel" of that {@link County}. Will be used as unique identifier.
     * @param name the human readable name of the {@link County}.
     */
    public County(final String ags, final String name) {
        this.ags = ags;
        this.name = name;
        this.statistics = new ArrayList<>();
    }

    /**
     * Add a new {@link Statistic} on a specified date to the {@link County}.
     *
     * @param sevenDayIncidence the seven day incidence to be stored in the statistic
     * @param rkiDate the date on which the RKI released the data
     * @return the newly created {@link Statistic} to process it further (e.g. persisting it etc.)
     */
    public Statistic addStatisticForDate(final Double sevenDayIncidence, final LocalDate rkiDate) {
        final Statistic statistic = new Statistic(sevenDayIncidence, rkiDate, this);

        this.statistics.add(statistic);

        return statistic;
    }

    /**
     * Searches for the latest {@link Statistic} for the {@link County}.
     *
     * @return the latest {@link Statistic} stored for this {@link County}
     * @throws IllegalStateException when the {@link County} has no {@link Statistic} applied.
     */
    public Statistic findLatestStatistic() {
        return statistics.stream().max(Comparator.comparing(Statistic::getRkiDate))
                .orElseThrow(() -> new IllegalStateException("Expecting County to have at least one statistic"));
    }

    /**
     * Filters all {@link Statistics} for the last fourteen days in the past based on the current date.
     *
     * @return an <i>unmodifiable</i> list containing the statistics of the last fourteen days in the past
     */
    public List<Statistic> findStatisticsForLastFourteenDays() {
        return statistics.stream().filter(stat -> stat.getRkiDate().isAfter(LocalDate.now().minusDays(STATISTIC_HISTORY_DAYS)))
                .collect(Collectors.toUnmodifiableList());
    }

    public String getAgs() {
        return ags;
    }

    public void setAgs(final String ags) {
        this.ags = ags;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ags, name);
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
        final County other = (County) obj;
        return Objects.equals(ags, other.ags) && Objects.equals(name, other.name);
    }

    @Override
    public String toString() {
        return "County [ags=" + ags + ", name=" + name + "]";
    }
}
