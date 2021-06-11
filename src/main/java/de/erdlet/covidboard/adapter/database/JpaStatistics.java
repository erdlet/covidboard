package de.erdlet.covidboard.adapter.database;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.erdlet.covidboard.domain.County;
import de.erdlet.covidboard.domain.LatestCountyStatistic;
import de.erdlet.covidboard.domain.Statistic;
import de.erdlet.covidboard.domain.Statistics;

@ApplicationScoped
public class JpaStatistics implements Statistics {

    @PersistenceContext(name = "covidboard")
    EntityManager em;

    @Override
    public void insert(final Statistic statistic) {
        Objects.requireNonNull(statistic, "statistic mustn't be null");

        em.persist(statistic);
    }

    @Override
    public boolean containsStatisticForDateAndCounty(final LocalDate date, final County county) {
        return em.createNamedQuery(Statistic.FIND_FOR_DATE, Long.class)
                .setParameter("rkiDate", date)
                .setParameter("county", county)
                .getResultList()
                .size() > 0;
    }

    @Override
    public List<LatestCountyStatistic> findLatestStatisticsForCounties() {
        return em.createNamedQuery(Statistic.FIND_LATEST_STATISTICS, LatestCountyStatistic.class).getResultList();
    }

    @Override
    public List<LatestCountyStatistic> findLatestStatisticsForCountyFilter(final String filter) {
        return em.createNamedQuery(Statistic.FIND_LATEST_STATISTICS_FOR_FILTER, LatestCountyStatistic.class)
                .setParameter("filterQuery", QueryUtils.formatParameterForContainsQuery(filter))
                .getResultList();
    }

    @Override
    public List<LatestCountyStatistic> findLatestStatisticsForAgsList(final List<String> ags) {
        return em.createNamedQuery(Statistic.FIND_STATISTICS_FOR_AGS, LatestCountyStatistic.class).setParameter("agsList", ags).getResultList();
    }
}
