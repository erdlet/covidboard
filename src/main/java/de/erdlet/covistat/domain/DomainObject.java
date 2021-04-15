package de.erdlet.covistat.domain;

/**
 * Common definition of an object representing a domain concept.
 *
 * @param <T> the concrete domain object's type
 *
 * @author erdlet
 *
 */
public interface DomainObject<T> {

    /**
     * Checks if two domain objects are the same by domain specific aspects.
     *
     * @param other the other domain object
     * @return <code>true</code> when the objects are the same, <code>false</code> when not
     */
    boolean isSameAs(T other);

}
