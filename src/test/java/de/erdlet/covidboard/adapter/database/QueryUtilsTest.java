package de.erdlet.covidboard.adapter.database;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class QueryUtilsTest {

    @Test
    public void formatParameterForContainsQuery_shouldFormatCorrectValue() {
        assertThat(QueryUtils.formatParameterForContainsQuery("foobar")).isEqualTo("%foobar%");
    }

}
