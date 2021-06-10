package de.erdlet.covidboard.adapter.database;

/**
 * Utility class for tasks related to JPQL / SQL queries.
 *
 * @author erdlet
 *
 */
public final class QueryUtils {

    /**
     * <p>
     * Formats a parameter to be used in a 'contains' semantic in a SQL query.
     * </p>
     *
     * <p>
     * For example, a value 'foobar' should be used in a LIKE to be checked for containment in another string. Therefore
     * this value needs to be wrapped in '%'.
     * </p>
     *
     * @param parameter the value which shall be checked for containment in another value.
     * @return the parameter wrapped in '%' to be used as <code>table.column LIKE %parameter%</code>
     */
    public static String formatParameterForContainsQuery(final String parameter) {
        return String.format("%%%s%%", parameter);
    }
}
