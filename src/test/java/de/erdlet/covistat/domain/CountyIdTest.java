package de.erdlet.covistat.domain;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class CountyIdTest {

    @Test
    public void constructorShouldTrimLeadingZeroesFromAgs() {
        final CountyId id = new CountyId("09185");

        assertThat(9185L).isEqualByComparingTo(id.getId());
    }

    @Test
    public void isSameAsShouldBeTrueWhenIdsAreEqual() {
        final CountyId id = new CountyId("09185");

        assertThat(id.isSameAs(new CountyId("09185"))).isTrue();
    }

    @Test
    public void isSameAsShouldBeFalseWhenIdsAreNotEqual() {
        final CountyId id = new CountyId("09185");

        assertThat(id.isSameAs(new CountyId("09186"))).isFalse();

    }
}
