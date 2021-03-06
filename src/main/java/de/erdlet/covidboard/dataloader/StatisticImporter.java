package de.erdlet.covidboard.dataloader;

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

import de.erdlet.covidboard.domain.Counties;
import de.erdlet.covidboard.domain.County;
import de.erdlet.covidboard.domain.Statistic;
import de.erdlet.covidboard.domain.Statistics;

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
            } else if (attr.ags != null && !attr.ags.isBlank()) {
                createCountyAndAddStatistic(attr);
            } else {
                LOGGER.warn("Could not import data for FeatureAttribute <{}> due to missing ags", attr);
            }
        }
    }

    private void addStatisticToExistingCounty(final Map<String, County> agsToCounty, final APIResponseFeatureAttributes attr) {
        final County county = agsToCounty.get(attr.ags);

        final var updateTimestamp = attr.parseLastUpdateAsDate();

        if (updateTimestamp == null) {
            LOGGER.warn("No update timestamp available for county {} with AGS {}. Won't perform import", attr.county, attr.ags);
            return;
        }

        final boolean statisticsAvailable = statistics.containsStatisticForDateAndCounty(updateTimestamp, county);
        if (!statisticsAvailable) {
            LOGGER.info("No statistics available for date {} in county with AGS {}. Adding new statistic...", updateTimestamp, attr.ags);
            storeStatistic(attr, county);
        } else {
            LOGGER.info("Statistics for date {} in county with AGS {} already exist.", updateTimestamp, attr.ags);
        }
    }

    private void createCountyAndAddStatistic(final APIResponseFeatureAttributes attr) {
        final County countyToCreate = new County(attr.ags, attr.county);
        counties.insert(countyToCreate);

        LOGGER.info("Created new County {} for AGS {}", countyToCreate, attr.ags);

        storeStatistic(attr, countyToCreate);
    }

    private void storeStatistic(final APIResponseFeatureAttributes attr, final County county) {
        final var updateTimestamp = attr.parseLastUpdateAsDate();

        if (updateTimestamp == null) {
            LOGGER.warn("No update timestamp available for county {} with AGS {}. Won't perform import", attr.county, attr.ags);
            return;
        }

        final Statistic statistic = county.addStatisticForDate(attr.casesInSevenDaysPer100KPeople, updateTimestamp);
        statistics.insert(statistic);

        LOGGER.info("Created new statistic {} for County with AGS {}", statistic, attr.ags);
    }
}
