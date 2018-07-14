package com.chosencraft.www.modreqrevised.database.sql.query.queries;

import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.ModReqRevisedMain;
import com.chosencraft.www.modreqrevised.database.sql.query.Query;
import com.chosencraft.www.modreqrevised.utils.Config;
import com.chosencraft.www.modreqrevised.utils.RequestState;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ReopenQuery implements Query
{

    private int requestID;

    public ReopenQuery(int requestID)
    {
        this.requestID = requestID;
    }

    @Override
    public PreparedStatement getQuery() throws SQLException
    {
        PreparedStatement statement = ModReqRevisedMain.database.createPreparedStatement("UPDATE " + Config.SQL_TABLE_NAME + " SET `requestState`=? WHERE `RequestID`=?;");

        //statement.setString(1, Config.SQL_TABLE_NAME);
        statement.setString(1, RequestState.UNCLAIMED.toString());
        statement.setInt(2,requestID);
        return statement;

    }

}
