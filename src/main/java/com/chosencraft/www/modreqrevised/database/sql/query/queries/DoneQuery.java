package com.chosencraft.www.modreqrevised.database.sql.query.queries;

import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.ModReqRevisedMain;
import com.chosencraft.www.modreqrevised.database.sql.query.Query;
import com.chosencraft.www.modreqrevised.utils.Config;
import com.chosencraft.www.modreqrevised.utils.RequestState;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DoneQuery implements Query
{

    private ModReq request;

    public DoneQuery(ModReq request)
    {
        this.request = request;
    }

    @Override
    public PreparedStatement getQuery() throws SQLException
    {
        PreparedStatement statement = ModReqRevisedMain.database.createPreparedStatement("UPDATE " + Config.SQL_TABLE_NAME + " SET `requestState`=? , `taskOwnerUUID`=? , `taskOwnerName`=? , `taskResolution`=? WHERE `requestID`=?  ;");

        //statement.setString(1, Config.SQL_TABLE_NAME);
        statement.setString(1, RequestState.FINISHED.toString());
        statement.setString(2, request.getTaskOwnerUUID().toString());
        statement.setString(3, request.getTaskOwner());
        statement.setString(4, request.getTaskResolution());
        statement.setString(5, Integer.toString(request.getID()));

        return statement;

    }
}