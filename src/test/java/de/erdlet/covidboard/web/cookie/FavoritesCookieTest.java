package de.erdlet.covidboard.web.cookie;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class FavoritesCookieTest {

    @Test
    public void extractFavoritesAsList_shouldReturnEmptyListWhenNoItemsExist() {
        final List<String> items = FavoritesCookie.extractFavoritesAsList("");

        assertThat(items.isEmpty());
    }

    @Test
    public void extractFavoritesAsList_shouldReturnSingleItemListWhenOnlyOneItemExists() {
        final List<String> items = FavoritesCookie.extractFavoritesAsList("09167");

        assertThat(items).containsOnly("09167");
    }

    @Test
    public void extractFavoritesAsList_shouldReturnMultiItemListWhenMultipleItemsExist() {
        final List<String> items = FavoritesCookie.extractFavoritesAsList("09167&09187");

        assertThat(items).containsOnly("09167", "09187");
    }

    @Test
    public void formatUpdatedValue_shouldReturnEmptyStringWhenListIsEmpty() {
        final List<String> emptyList = List.of();

        assertThat(FavoritesCookie.formatFavoritesToCookieValue(emptyList)).isEqualTo("");
    }

    @Test
    public void formatUpdatedValue_shouldReturnSingleItemStringWhenOnlyOneItemIsStored() {
        final List<String> singleItemList = List.of("09167");

        assertThat(FavoritesCookie.formatFavoritesToCookieValue(singleItemList)).isEqualTo("09167");
    }

    @Test
    public void formatUpdatedValue_shouldReturnMultiItemStringWhenMultipleValuesAreStored() {
        final List<String> multiItemList = List.of("09167", "09187");

        assertThat(FavoritesCookie.formatFavoritesToCookieValue(multiItemList)).isEqualTo("09167&09187");
    }
}
