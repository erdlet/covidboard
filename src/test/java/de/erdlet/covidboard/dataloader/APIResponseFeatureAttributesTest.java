package de.erdlet.covidboard.dataloader;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.Test;

public class APIResponseFeatureAttributesTest {

    @Test
    public void parseLastUpdateAsDate_shouldReturnCorrectDate() {

        final APIResponseFeatureAttributes attributes = new APIResponseFeatureAttributes();
        attributes.lastUpdate = "14.04.2021, 00:00 Uhr";

        final LocalDate actual = attributes.parseLastUpdateAsDate();

        assertThat(actual).isEqualTo(LocalDate.of(2021, 4, 14));
    }
}
