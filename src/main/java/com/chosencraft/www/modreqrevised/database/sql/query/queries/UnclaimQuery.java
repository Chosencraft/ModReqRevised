package com.chosencraft.www.modreqrevised.database.sql.query.queries;

import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.ModReqRevisedMain;
import com.chosencraft.www.modreqrevised.database.sql.query.Query;
import com.chosencraft.www.modreqrevised.utils.Config;
import com.chosencraft.www.modreqrevised.utils.RequestState;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UnclaimQuery implements Query
{

    private ModReq request;

    public UnclaimQuery(ModReq request)
    {
        this.request  = request;
    }

    @Override
    public PreparedStatement getQuery() throws SQLException
    {
            PreparedStatement statement = ModReqRevisedMain.database.createPreparedStatement("UPDATE ? SET 'requestState'=?, 'taskOwnerName'='null', taskOwnerUUID='null' ;");

            statement.setString(1, Config.SQL_TABLE_NAME);
            statement.setString(2, RequestState.UNCLAIMED.toString());

            return statement;

    }
}
