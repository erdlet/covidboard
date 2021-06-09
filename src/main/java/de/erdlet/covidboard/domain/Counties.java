package de.erdlet.covidboard.domain;

import java.util.List;
import java.util.Optional;

/**
 * Interface for accessing {@link County} data.
 *
 * @author erdlet
 *
 */
public interface Counties {

    /**
     * Inserts a new {@link County} into the datastore. Performs no check if it's already registered.
     *
     * @param county the {@link County} which is going to be saved in the datastore. Mustn't be null.
     */
    void insert(final County county);

    /**
     * Reads all stored {@link County} objects in the order they are created.
     *
     * @return a List of all existing {@link County} objects. Will never be null.
     */
    List<County> findAll();

    /**
     * Reads all stored {@link County} objects and orders them by their AGS in ascending order.
     *
     * @return a List of all existing {@link County} objects ordered ascending by their AGS. Will never be null.
     */
    List<County> findAllOrderedByAgs();

    /**
     * Searches for a {@link County} by its AGS and loads also all applied statistics.
     *
     * @param ags the AGS for which the {@link County} shall be resolved
     * @return either the {@link County} or an empty result
     */
    Optional<County> findByAgsWithStatistics(final String ags);
}
