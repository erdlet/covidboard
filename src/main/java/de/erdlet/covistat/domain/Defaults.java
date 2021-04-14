package de.erdlet.covistat.domain;

import java.time.ZoneId;

/**
 * Collection of default settings and assumptions within the domain.
 *
 * @author erdlet
 *
 */
public interface Defaults {

    /**
     * The Timezone ID of Berlin to parse datetimes correct.
     */
    ZoneId EUROPE_BERLIN = ZoneId.of("Europe/Berlin");

}
