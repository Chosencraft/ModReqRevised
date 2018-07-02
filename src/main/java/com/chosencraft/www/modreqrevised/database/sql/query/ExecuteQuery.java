package com.chosencraft.www.modreqrevised.database.sql.query;

import java.sql.ResultSet;

public interface ExecuteQuery extends Query
{
    public void execute(ResultSet results);
}
