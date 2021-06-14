package de.erdlet.covidboard.web;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

/**
 * Tests only a few small methods, as I'd need a JAX-RS runtime for the rest.
 * @author erdlet
 *
 */
public class FavoritesControllerTest {

    // Not configured completely, as only small methods can be tested.
    private final FavoritesController sut = new FavoritesController();

    @Test
    public void extractFavoritesAsList_shouldReturnEmptyListWhenNoItemsExist() {
        final List<String> items = sut.extractFavoritesAsList("");

        assertThat(items.isEmpty());
    }

    @Test
    public void extractFavoritesAsList_shouldReturnSingleItemListWhenOnlyOneItemExists() {
        final List<String> items = sut.extractFavoritesAsList("09167");

        assertThat(items).containsOnly("09167");
    }

    @Test
    public void extractFavoritesAsList_shouldReturnMultiItemListWhenMultipleItemsExist() {
        final List<String> items = sut.extractFavoritesAsList("09167&09187");

        assertThat(items).containsOnly("09167", "09187");
    }

    @Test
    public void formatUpdatedValue_shouldReturnEmptyStringWhenListIsEmpty() {
        final List<String> emptyList = List.of();

        assertThat(sut.formatUpdatedValue(emptyList)).isEqualTo("");
    }

    @Test
    public void formatUpdatedValue_shouldReturnSingleItemStringWhenOnlyOneItemIsStored() {
        final List<String> singleItemList = List.of("09167");

        assertThat(sut.formatUpdatedValue(singleItemList)).isEqualTo("09167");
    }

    @Test
    public void formatUpdatedValue_shouldReturnMultiItemStringWhenMultipleValuesAreStored() {
        final List<String> multiItemList = List.of("09167", "09187");

        assertThat(sut.formatUpdatedValue(multiItemList)).isEqualTo("09167&09187");
    }
}
