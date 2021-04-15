package de.erdlet.covistat.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.time.LocalDateTime;

import org.junit.Test;

public class CountyStatisticTest {

    private static final CountyStatistic SUT = new CountyStatistic("09185", "LK Neuburg-Schrobenhausen", 150.0D, "15.04.2021, 00:00 Uhr");

    @Test
    public void shouldCreateCountyId() {
        assertThat(SUT.getId().isSameAs(new CountyId("09185"))).isTrue();
    }

    @Test
    public void shouldCreateCorrectInstantForTimestamp() {
        final Instant expected = LocalDateTime.of(2021, 4, 15, 0, 0).atZone(Defaults.GERMAN_TIMEZONE).toInstant();

        assertThat(SUT.getReportTimestamp()).isEqualTo(expected);
    }
}
