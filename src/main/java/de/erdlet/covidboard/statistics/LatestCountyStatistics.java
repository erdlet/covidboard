package de.erdlet.covidboard.statistics;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.erdlet.covidboard.domain.County;
import de.erdlet.covidboard.domain.LatestCountyStatistic;
import de.erdlet.covidboard.domain.Statistics;;

@ApplicationScoped
public class LatestCountyStatistics {

    private static final Logger LOGGER = LoggerFactory.getLogger(LatestCountyStatistics.class);

    @Inject
    Statistics statistics;

    /**
     * Loads the latest {@link Statistics} for ALL {@link County}s existing in the database.<br>
     * <br>
     * <b>Attention:</b> To avoid performance issues, only use with caution.
     *
     * @return the {@link LatestCountyStatistic} of all {@link County}s existing in the database. Will never be null.
     */
    @Transactional
    public List<LatestCountyStatistic> loadAll() {
        final List<LatestCountyStatistic> latestStatistics = statistics.findLatestStatisticsForCounties();

        LOGGER.debug("Fetched latest statistics: {}", latestStatistics);

        return latestStatistics;
    }

    /**
     * Searches for latest {@link Statistics} based on a filter and excluding an users favorites.
     *
     * @param filter the filter pattern to applied. Can be a part of the {@link County#name} or {@link County#ags}. Must not
     * be null.
     * @param favorites a list of AGS from a users favored {@link County}s. Must not be null. Can be empty.
     * @return an empty List in case no result can be found or the filter is empty. Otherwise the list with the found
     * {@link LatestCountyStatistic}s.
     */
    @Transactional
    public List<LatestCountyStatistic> searchWithFilterExcludingFavorites(final String filter, final List<String> favorites) {
        Objects.requireNonNull(filter, "filter must not be null");
        LOGGER.debug("Searching latest statistics for filter: {}", filter);

        Objects.requireNonNull(favorites, "favorites must not be null");
        LOGGER.debug("Excluding favorites from search: {}", favorites);

        final List<LatestCountyStatistic> latestStatisticsForFilter = favorites.isEmpty()
                ? statistics.findLatestStatisticsForFilter(filter)
                        : statistics.findLatestStatisticsForCountyFilterWithoutFavorites(filter, favorites);
        LOGGER.debug("Found latest statistics for filter '{}': {}", filter, latestStatisticsForFilter);

        return latestStatisticsForFilter;
    }

    /**
     * Load the latest statistics for an users favorites.
     *
     * @param ags the list of AGS which belong to the {@link County}s an user favors. Must not be null. Can be empty.
     * @return an empty List if the #ags are empty. Otherwise a list with the found {@link LatestCountyStatistic}s.
     */
    @Transactional
    public List<LatestCountyStatistic> loadFavorites(final List<String> ags) {
        Objects.requireNonNull(ags, "filter must not be null");
        LOGGER.debug("Searching latest statistics for AGS list: {}", ags);

        if (ags.isEmpty()) {
            LOGGER.debug("No AGS provided in list. Returning empty result.");
            return List.of();
        }

        final List<LatestCountyStatistic> latestStatisticsForAgsList = statistics.findLatestStatisticsForAgsList(ags);
        LOGGER.debug("Found latest statistics for AGS list <{}>: {}", ags, latestStatisticsForAgsList);

        return latestStatisticsForAgsList;
    }
}
