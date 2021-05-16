package de.erdlet.covistat.dataloader;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.ejb.Schedule;
import javax.ejb.Schedules;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.erdlet.covistat.domain.Counties;
import de.erdlet.covistat.domain.County;
import de.erdlet.covistat.domain.Statistic;
import de.erdlet.covistat.domain.Statistics;

@Startup
@Singleton
public class StatisticImporter {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatisticImporter.class);

    @Inject
    Counties counties;

    @Inject
    Statistics statistics;

    @Inject
    APIClient apiClient;

    @PostConstruct
    public void importOnStartupIfDataIsOutdated() {
        importNewStatistics();
    }

    @Schedules({
        @Schedule(minute = "0", hour = "2"),
        @Schedule(minute = "0", hour = "5"),
        @Schedule(minute = "0", hour = "7")
    })
    @Transactional(TxType.REQUIRES_NEW)
    public void importNewStatistics() {
        LOGGER.info("Starting import of statistics...");

        final List<APIResponseFeature> apiResult = apiClient.fetchStatistics();

        if (apiResult.isEmpty()) {
            LOGGER.warn("No information available from API. End import.");
        } else {
            LOGGER.info("Information fetched from API. Beginning import to database...");
            startImportForApiResult(apiResult);
        }
    }

    private void startImportForApiResult(final List<APIResponseFeature> apiResult) {
        final Map<String, County> agsToCounty = counties.findAll().stream().collect(Collectors.toMap(County::getAgs, Function.identity()));

        for (final APIResponseFeature feature : apiResult) {
            final APIResponseFeatureAttributes attr = feature.attributes;
            LOGGER.info("Processing FeatureAttributes for AGS {}", attr.ags);

            if (agsToCounty.containsKey(attr.ags)) {
                LOGGER.info("Found County for AGS {}", attr.ags);
                addStatisticToExistingCounty(agsToCounty, attr);
            } else {
                createCountyAndAddStatistic(attr);
            }
        }
    }

    private void addStatisticToExistingCounty(final Map<String, County> agsToCounty, final APIResponseFeatureAttributes attr) {
        final County county = agsToCounty.get(attr.ags);

        final boolean statisticsAvailable = statistics.containsStatisticForDateAndCounty(attr.parseLastUpdateAsDate(), county);
        if (!statisticsAvailable) {
            LOGGER.info("No statistics available for date {} in county with AGS {}. Adding new statistic...", attr.parseLastUpdateAsDate(), attr.ags);
            storeStatistic(attr, county);
        } else {
            LOGGER.info("Statistics for date {} in county with AGS {} already exist.", attr.parseLastUpdateAsDate(), attr.ags);
        }
    }

    private void createCountyAndAddStatistic(final APIResponseFeatureAttributes attr) {
        final County countyToCreate = new County(attr.ags, attr.county);
        counties.insert(countyToCreate);

        LOGGER.info("Created new County {} for AGS {}", countyToCreate, attr.ags);

        storeStatistic(attr, countyToCreate);
    }

    private void storeStatistic(final APIResponseFeatureAttributes attr, final County county) {
        final Statistic statistic = county.addStatisticForDate(attr.casesInSevenDaysPer100KPeople, attr.parseLastUpdateAsDate());
        statistics.insert(statistic);

        LOGGER.info("Created new statistic {} for County with AGS {}", statistic, attr.ags);
    }
}
