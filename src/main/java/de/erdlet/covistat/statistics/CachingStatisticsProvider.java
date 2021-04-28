package de.erdlet.covistat.statistics;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.erdlet.covistat.dataloader.APIClient;
import de.erdlet.covistat.dataloader.APIResponseFeature;

@Startup
@Singleton
public class CachingStatisticsProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(CachingStatisticsProvider.class);

    private static final List<APIResponseFeature> FEATURE_CACHE = new ArrayList<>();

    @Inject
    APIClient client;

    public List<APIResponseFeature> getRawStatisticData() {
        if (FEATURE_CACHE.isEmpty()) {
            LOGGER.info("Feature data cache is empty. Loading features from API...");
            FEATURE_CACHE.addAll(client.fetchStatistics());
            LOGGER.info("Feature data loaded from API successfully!");
        }

        return List.copyOf(FEATURE_CACHE);
    }

    @Schedule(hour = "1")
    public void refreshCache() {
        LOGGER.info("Starting to refresh feature data cache...");

        FEATURE_CACHE.clear();
        LOGGER.info("Cleared feature data cache...");

        FEATURE_CACHE.addAll(client.fetchStatistics());
        LOGGER.info("Fetched new statistics successfully!");
    }
}
