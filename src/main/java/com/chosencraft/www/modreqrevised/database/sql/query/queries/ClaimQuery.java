package com.chosencraft.www.modreqrevised.database.sql.query.queries;

import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.ModReqRevisedMain;
import com.chosencraft.www.modreqrevised.database.sql.query.Query;
import com.chosencraft.www.modreqrevised.utils.Config;
import com.chosencraft.www.modreqrevised.utils.RequestState;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClaimQuery implements Query
{

    private ModReq request;

    public  ClaimQuery(ModReq request)
    {
        this.request = request;
    }

    @Override
    public PreparedStatement getQuery() throws SQLException
    {
        PreparedStatement statement = ModReqRevisedMain.database.createPreparedStatement("UPDATE " + Config.SQL_TABLE_NAME + " SET `taskOwnerUUID`=? , `taskOwnerName`=? , `requestState`=? WHERE `requestID`=? ;");

       // statement.setString(1, Config.SQL_TABLE_NAME);
        statement.setString(1, request.getTaskOwnerUUID().toString());
        statement.setString(2,request.getTaskOwner());
        statement.setString(3, RequestState.CLAIMED.toString());
        statement.setString(4, Integer.toString(request.getID()));

        return statement;

    }

}
