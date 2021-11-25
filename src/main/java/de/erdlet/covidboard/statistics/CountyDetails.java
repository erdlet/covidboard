package de.erdlet.covidboard.statistics;

import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import de.erdlet.covidboard.domain.Counties;
import de.erdlet.covidboard.domain.County;
import de.erdlet.covidboard.domain.CountyDetail;

@ApplicationScoped
public class CountyDetails {

    @Inject
    Counties counties;

    /**
     * Searches for the details of a {@link County} based on its AGS.
     *
     * @param ags the AGS for which the {@link County} shall be loaded
     * @return either the found {@link County} or an empty result
     */
    @Transactional
    public Optional<CountyDetail> findCountyDetailsForAgs(final String ags) {
        return counties.findByAgsWithStatistics(ags)
                .map(county -> new CountyDetail(county, county.findLatestStatistic(), county.findStatisticsForLastFourteenDays()));
    }
}
