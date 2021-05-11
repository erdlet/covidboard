package de.erdlet.covistat.adapter.database;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.erdlet.covistat.domain.County;
import de.erdlet.covistat.domain.LatestCountyStatistic;
import de.erdlet.covistat.domain.Statistic;
import de.erdlet.covistat.domain.Statistics;

@ApplicationScoped
public class JpaStatistics implements Statistics {

    @PersistenceContext(name = "covistat")
    EntityManager em;

    @Override
    public void insert(final Statistic statistic) {
        Objects.requireNonNull(statistic, "statistic mustn't be null");

        em.persist(statistic);
    }

    @Override
    public boolean containsStatisticForDateAndCounty(final LocalDate date, final County county) {
        return em.createNamedQuery(Statistic.FIND_FOR_DATE, Long.class).setParameter("rkiDate", date).setParameter("county", county).getResultList()
                .size() > 0;
    }

    @Override
    public List<LatestCountyStatistic> findLatestStatisticsForCounties() {
        return em.createNamedQuery(Statistic.FIND_LATEST_STATISTICS, LatestCountyStatistic.class).getResultList();
    }
}
