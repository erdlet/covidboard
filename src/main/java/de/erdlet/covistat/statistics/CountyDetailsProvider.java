package de.erdlet.covistat.statistics;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import de.erdlet.covistat.domain.Counties;
import de.erdlet.covistat.domain.CountyDetails;

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
