package com.chosencraft.www.modreqrevised.database.sql.query.queries;

import com.chosencraft.www.modreqrevised.Cache;
import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.ModReqRevisedMain;
import com.chosencraft.www.modreqrevised.database.sql.query.ExecuteQuery;
import com.chosencraft.www.modreqrevised.utils.Config;
import com.chosencraft.www.modreqrevised.utils.Logger;
import com.chosencraft.www.modreqrevised.utils.RequestState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GetModReqIDQuery implements ExecuteQuery
{

    private ModReq req;

    public GetModReqIDQuery(ModReq req)
    {
        this.req = req;
    }

    @Override
    public void execute(ResultSet results)
    {
        Player sender = Bukkit.getPlayerExact(req.getRequester());
        if(sender != null)
        {
            try
            {
                if(results.first())
                {
                    req.setID(results.getInt(1));
                    Cache.addModReq(req);
                }
            }
            catch(SQLException sqlException)
            {
                ModReqRevisedMain.logger.logError("Failed to get ModReq ID! " + sqlException.getMessage() + sqlException.getErrorCode() );
                sqlException.printStackTrace();
            }
        }

    }

    @Override
    public PreparedStatement getQuery() throws SQLException
    {
        PreparedStatement statement = ModReqRevisedMain.database.createPreparedStatement("SELECT  MAX(`requestID`) FROM ? GROUP BY requestID ;");

        statement.setString(1, Config.SQL_TABLE_NAME);

        return statement;
    }
}
