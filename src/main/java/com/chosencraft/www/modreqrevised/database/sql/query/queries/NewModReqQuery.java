package com.chosencraft.www.modreqrevised.database.sql.query.queries;

import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.ModReqRevisedMain;
import com.chosencraft.www.modreqrevised.database.sql.Consumer;
import com.chosencraft.www.modreqrevised.database.sql.query.Query;
import com.chosencraft.www.modreqrevised.utils.Config;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NewModReqQuery implements Query
{

    private ModReq req;

    public NewModReqQuery(ModReq req)
    {
        this.req = req;
        Consumer.queue(this);
        Consumer.queue(new GetModReqIDQuery(req));
        Consumer.queue(new UpdateCacheQuery());
    }


    @Override
    public PreparedStatement getQuery() throws SQLException
    {
        PreparedStatement statement = ModReqRevisedMain.database.createPreparedStatement("INSERT INTO " + Config.SQL_TABLE_NAME +" (`requesterName`, `requesterUUID`, `worldUUID`, `xLocation`, `yLocation`, `zLocation`, `requestMessage`, `requestState`) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?) ;");

        //statement.setString(1, Config.SQL_TABLE_NAME);
        statement.setString(1, req.getRequester());
        statement.setString(2, req.getRequesterUUID().toString());
        statement.setString(3, req.getRequesterLocation().getWorld().getUID().toString());
        statement.setString(4, Integer.toString(req.getRequesterLocation().getBlockX()));
        statement.setString(5, Integer.toString(req.getRequesterLocation().getBlockY()));
        statement.setString(6, Integer.toString(req.getRequesterLocation().getBlockZ()));
        statement.setString(7, req.getRequestMessage());
        statement.setString(8, req.getState().toString());

        return statement;
    }
}
