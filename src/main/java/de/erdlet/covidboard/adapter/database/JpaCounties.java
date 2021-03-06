package de.erdlet.covidboard.adapter.database;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import de.erdlet.covidboard.domain.Counties;
import de.erdlet.covidboard.domain.County;

/**
 * JPA implementation of the {@link Counties} interface for accessing the {@link County} data.
 *
 * @author erdlet
 *
 */
@ApplicationScoped
public class JpaCounties implements Counties {

    @PersistenceContext(name = DatabaseConstants.PERSISTENCE_UNIT_COVIDBOARD)
    EntityManager em;

    @Override
    public void insert(final County county) {
        em.persist(county);
    }

    @Override
    public List<County> findAll() {
        return em.createNamedQuery(County.FIND_ALL, County.class).getResultList();

    }

    @Override
    public List<County> findAllOrderedByAgs() {
        return em.createNamedQuery(County.FIND_ALL_ORDERED_BY_AGS, County.class).getResultList();
    }

    @Override
    public Optional<County> findByAgsWithStatistics(final String ags) {
        return em.createNamedQuery(County.FIND_BY_AGS_WITH_STATISTICS, County.class).setParameter("ags", ags).getResultStream().findFirst();
    }
}
