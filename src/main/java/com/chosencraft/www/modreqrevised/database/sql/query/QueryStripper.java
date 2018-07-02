package com.chosencraft.www.modreqrevised.database.sql.query;

public class QueryStripper
{
    /**
     * Parses query
     */
    private String stripQuery(String query)
    {
        // word characters , space, @, and hyphens
        String regex = "[^\\w\\s\\.@-]";

        // strip with regex pattern
        return query.replaceAll(regex, "");

    }
}
