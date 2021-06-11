package de.erdlet.covidboard.statistics;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.erdlet.covidboard.dataloader.StatisticImporter;
import de.erdlet.covidboard.domain.LatestCountyStatistic;
import de.erdlet.covidboard.domain.Statistics;

@ApplicationScoped
public class DatabaseStatisticsProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseStatisticsProvider.class);

    @Inject
    StatisticImporter statisticImporter;

    @Inject
    Statistics statistics;

    @Transactional
    public List<LatestCountyStatistic> loadLatestCountyStatistics() {
        final List<LatestCountyStatistic> latestStatistics = statistics.findLatestStatisticsForCounties();

        if (latestStatistics.isEmpty()) {
            LOGGER.warn("No statistics found. Loading them manually...");
            statisticImporter.importNewStatistics();

            LOGGER.info("Statistics loaded. Fetching an returning them again...");

            return statistics.findLatestStatisticsForCounties();
        }

        LOGGER.debug("Fetched latest statistics: {}", latestStatistics);

        return latestStatistics;
    }

    @Transactional
    public List<LatestCountyStatistic> loadLatestCountyStatisticsForFilter(final String filter) {
        Objects.requireNonNull(filter, "filter must not be null");
        LOGGER.debug("Searching latest statistics for filter: {}", filter);

        final List<LatestCountyStatistic> latestStatisticsForFilter = statistics.findLatestStatisticsForCountyFilter(filter);
        LOGGER.debug("Found latest statistics for filter '{}': {}", filter, latestStatisticsForFilter);

        return latestStatisticsForFilter;
    }

    @Transactional
    public List<LatestCountyStatistic> loadLatestCountyStatisticsForAgsList(final List<String> ags) {
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
