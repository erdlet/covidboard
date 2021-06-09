package de.erdlet.covidboard.statistics;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import de.erdlet.covidboard.domain.Counties;
import de.erdlet.covidboard.domain.CountyDetails;

@ApplicationScoped
public class CountyDetailsProvider {

    @Inject
    Counties counties;

    @Transactional
    public Optional<CountyDetails> findCountyDetailsForAgs(final String ags) {
        return counties.findByAgsWithStatistics(ags)
                .map(county -> new CountyDetails(county, county.findLatestStatistic(), county.findStatisticsForLastFourteenDays()));
    }
}
