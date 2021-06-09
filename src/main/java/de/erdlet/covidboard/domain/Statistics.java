package de.erdlet.covidboard.domain;

import java.time.LocalDate;
import java.util.List;

/**
 * Interface containing methods for accessings {@link Statistics} and related information.
 *
 * @author erdlet
 *
 */
public interface Statistics {

    /**
     * Inserts a new {@link Statistic} in the datastore.
     *
     * @param statistic the {@link Statistic} to insert
     */
    void insert(final Statistic statistic);

    /**
     * Checks if there is already a statistic for the current date in a specific {@link County}.
     *
     * @param date the date for which the existence of a {@link Statistic} shall be verified
     * @param county the {@link County} for which the existence of the {@link Statistic} shall be verified
     * @return <code>true</code> in case there is already a {@link Statistic}, <code>false</code> if not
     */
    boolean containsStatisticForDateAndCounty(final LocalDate date, final County county);

    /**
     * Collects the latest {@link Statistic}s for a {@link County}.
     *
     * @return List of the latest {@link Statistic}s for each saved {@link County}. Will never be null.
     */
    List<LatestCountyStatistic> findLatestStatisticsForCounties();
}
