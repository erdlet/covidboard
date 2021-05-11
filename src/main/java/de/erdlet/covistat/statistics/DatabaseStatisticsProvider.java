package de.erdlet.covistat.statistics;

import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.erdlet.covistat.dataloader.StatisticImporter;
import de.erdlet.covistat.domain.LatestCountyStatistic;
import de.erdlet.covistat.domain.Statistics;

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
}
