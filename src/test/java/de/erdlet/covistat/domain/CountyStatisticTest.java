package de.erdlet.covistat.domain;

import static org.junit.Assert.assertEquals;

import java.time.Instant;
import java.time.LocalDateTime;

import org.junit.Test;

public class CountyStatisticTest {

    private static final LocalDateTime TEST_TIMESTAMP = LocalDateTime.of(2021, 4, 14, 0, 0);
    private static final Instant TEST_INSTANT = TEST_TIMESTAMP.atZone(Defaults.EUROPE_BERLIN).toInstant();

    @Test
    public void parameterizedConstructorShouldSetAllFieldsCorrect() throws Exception {

        final CountyStatistic sut = new CountyStatistic("0123456", "LK Test", 190.99D, TEST_TIMESTAMP);

        assertEquals("0123456", sut.getAgs());
        assertEquals("LK Test", sut.getCountyName());
        assertEquals(190.99D, sut.getSevenDayIncidence(), 0D);
        assertEquals(TEST_INSTANT, sut.getReportTimestamp());
    }

    @Test
    public void getReportTimestampAsDateTimeShouldReturnCorrectTransformedValue() throws Exception {
        final CountyStatistic sut = new CountyStatistic("0123456", "LK Test", 190.99D, TEST_TIMESTAMP);

        assertEquals(TEST_TIMESTAMP, sut.getReportTimestampAsDateTime());
    }
}
