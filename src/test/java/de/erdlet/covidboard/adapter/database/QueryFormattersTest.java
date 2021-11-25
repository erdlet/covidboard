package de.erdlet.covidboard.adapter.database;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class QueryFormattersTest {

    @Test
    public void formatParameterForContainsQuery_shouldFormatCorrectValue() {
        assertThat(QueryParameterFormatters.formatParameterForContainsQuery("foobar")).isEqualTo("%foobar%");
    }

}
