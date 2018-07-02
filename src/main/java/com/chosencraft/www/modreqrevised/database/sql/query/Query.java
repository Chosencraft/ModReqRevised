package com.chosencraft.www.modreqrevised.database.sql.query;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Query
{
    public PreparedStatement getQuery() throws SQLException;
}
