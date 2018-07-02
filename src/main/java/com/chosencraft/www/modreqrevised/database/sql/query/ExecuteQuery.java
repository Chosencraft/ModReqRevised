package com.chosencraft.www.modreqrevised.database.sql.query;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ExecuteQuery extends Query
{
    public void execute(ResultSet results) throws SQLException;
}
