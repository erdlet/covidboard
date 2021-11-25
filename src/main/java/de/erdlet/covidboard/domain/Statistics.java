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

    /**
     * Collects the latest {@link Statistic}s for {@link County}s matching the filter.<br>
     * <br>
     * <b>Note:</b> Shall be used when there are no favorites, as JPA has problems handling empty lists in
     * <code>NOT IN</code> clauses.
     *
     * @param filter a filter provided by an user. The filter is expected to be a part of a {@link County}'s name or a (part
     * of) an AGS.
     * @return a {@link List} of the latest {@link Statistic}s where the {@link County} matches the filter
     */
    List<LatestCountyStatistic> findLatestStatisticsForFilter(final String filter);

    /**
     * Collects the latest {@link Statistic}s for {@link County}s matching the filter and ignoring existing favorites.
     *
     * @param filter a filter provided by an user. The filter is expected to be a part of a {@link County}'s name or a (part
     * of) an AGS.
     * @param favorites a list of favorites which shall not be shown in the UI. Mustn't be empty in JPA implementations, as
     * it has problems handling <code>NOT IN</code> with empty lists.
     * @return a {@link List} of the latest {@link Statistic}s where the {@link County} matches the filter
     */
    List<LatestCountyStatistic> findLatestStatisticsForCountyFilterWithoutFavorites(final String filter, final List<String> favorites);

    /**
     * Collects the latest {@link Statistic}s for the {@link County}s identified by the provided AGS.
     *
     * @param ags a {@link List} of AGS for which the latest statistics shall be loaded
     * @return a {@link List} of all found statistics for the AGS
     */
    List<LatestCountyStatistic> findLatestStatisticsForAgsList(final List<String> ags);
}
