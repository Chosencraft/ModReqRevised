package com.chosencraft.www.modreqrevised.database.sql.query.queries;

import com.chosencraft.purefocus.Chat;
import com.chosencraft.www.modreqrevised.Cache;
import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.ModReqRevisedMain;
import com.chosencraft.www.modreqrevised.database.sql.query.ExecuteQuery;
import com.chosencraft.www.modreqrevised.utils.Config;
import com.chosencraft.www.modreqrevised.utils.RequestState;
import org.bukkit.entity.Player;

import java.sql.*;
import java.util.UUID;

import static com.chosencraft.www.modreqrevised.utils.RequestState.*;

public class UpdateCacheQuery implements ExecuteQuery
{

    @Override
    public PreparedStatement getQuery() throws SQLException
    {
        PreparedStatement statement = ModReqRevisedMain.database.createPreparedStatement("SELECT * FROM " + Config.SQL_TABLE_NAME + " WHERE `requestState`=? OR `requestState`=? ;");

        //statement.setString(1, Config.SQL_TABLE_NAME);
        statement.setString(1, RequestState.UNCLAIMED.toString());
        statement.setString(2, CLAIMED.toString());

        return statement;
    }

    @Override
    public void execute(ResultSet results) throws SQLException
    {
        Cache.clearRequests();
        while(results.next())
        {
            UUID taskOwnerUUID = null;

            // Needed, otherwise if task owner is null, then the uuid conversion will throw an NPE
            if (results.getString(9) != null)
            {
                taskOwnerUUID = UUID.fromString(results.getString(9));
            }

           ModReq req =  new ModReq(
                    results.getString(2),
                    UUID.fromString(results.getString(3)),
                    UUID.fromString(results.getString(4)),
                    results.getInt(5),
                    results.getInt(6),
                    results.getInt(7),
                    results.getString(8),
                    results.getString(10),
                    taskOwnerUUID,
                   results.getString(11),
                   results.getTimestamp(12),
                   results.getString(13),
                   results.getInt(1)
            );

            Cache.addModReq(req);
        }
        if (Cache.requests.size() > 0)
        {
            for (Player player : Cache.notifies)
            {
                player.sendMessage(Chat.format("&cThere are still &b" + Cache.requests.size() + " &crequests!"));
            }
            ModReqRevisedMain.logger.logInfo(Chat.format("&cThere are still &b" + Cache.requests.size() + " &crequests!"));
        }
    }

}
