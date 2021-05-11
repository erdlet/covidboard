package de.erdlet.covistat.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
 * A {@link County} is a "kreisfreie Stad" or a "Landkreis" for which COVID-19 information is gathered by the RKI.
 *
 * Each {@link County} is uniquely identified by the "AGS", which means "Allgemeiner Gemeinde Schluessel".
 *
 * @author erdlet
 *
 */
@Entity
@Table(name = "counties")
@NamedQueries({
    @NamedQuery(name = "County.findAll", query = "SELECT c FROM County c"),
    @NamedQuery(name = "County.findAllOrderedByAgs", query = "SELECT c FROM County c ORDER BY ags")
})
public class County {

    public static final String FIND_ALL = "County.findAll";
    public static final String FIND_ALL_ORDERED_BY_AGS = "County.findAllOrderedByAgs";

    @Id
    private String ags;

    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "county_ags")
    private List<Statistic> statistics;

    public County() {
    }

    public County(final String ags, final String name) {
        this.ags = ags;
        this.name = name;
        this.statistics = new ArrayList<>();
    }

    public Statistic addStatisticForDate(final Double sevenDayIncidence, final LocalDate rkiDate) {
        final Statistic statistic = new Statistic(sevenDayIncidence, rkiDate, this);

        this.statistics.add(statistic);

        return statistic;
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
