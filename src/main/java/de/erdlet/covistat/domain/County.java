package de.erdlet.covistat.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A {@link County} is a "kreisfreie Stad" or a "Landkreis" for which COVID-19 information is gathered by the RKI.
 *
 * Each {@link County} is uniquely identified by the "AGS", which means "Allgemeiner Gemeinde Schluessel".
 *
 * @author erdlet
 *
 */
public class County {

    private String ags;

    private String name;

    private List<Statistic> statistics;

    public County() {
    }

    public County(final String ags, final String name) {
        this.ags = ags;
        this.name = name;
        this.statistics = new ArrayList<>();
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