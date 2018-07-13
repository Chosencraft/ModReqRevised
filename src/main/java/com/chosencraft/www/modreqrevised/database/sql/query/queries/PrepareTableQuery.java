package com.chosencraft.www.modreqrevised.database.sql.query.queries;

import com.chosencraft.www.modreqrevised.ModReqRevisedMain;
import com.chosencraft.www.modreqrevised.utils.Config;
import com.chosencraft.www.modreqrevised.utils.Logger;
import org.bukkit.plugin.Plugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.chosencraft.www.modreqrevised.ModReqRevisedMain.database;

public class PrepareTableQuery
{

    private Logger log = ModReqRevisedMain.logger;

    public PrepareTableQuery()
    {
        createRequestTable();
    }

    /**
     * Creates the needed table
     */
    private void createRequestTable()
    {
            try
            {
                Statement statement = database.createStatement();
                // Usernames are limited to 16 characters oddly enough, I expect that to change one day and break everything
                // only 256 chars can be used in a chat line, but special characters can be weird. so I'm doubling it
                statement.execute(
                        "CREATE TABLE IF NOT EXISTS " + Config.SQL_TABLE_NAME +
                                "(" +
                                "requestID INT NOT NULL AUTO_INCREMENT PRIMARY KEY," +
                                "requesterName CHAR(16) NOT NULL," +
                                "requesterUUID CHAR(36) NOT NULL," +
                                "worldUUID CHAR(36) NOT NULL," +
                                "xLocation INT NOT NULL," +
                                "yLocation INT NOT NULL," +
                                "zLocation INT NOT NULL," +
                                "requestMessage VARCHAR(512) NOT NULL," +
                                "taskOwnerUUID CHAR(36)," +
                                "taskOwnerName CHAR(16)," +
                                "taskResolution VARCHAR(512)," +
                                "timestamp DATETIME NOT NULL," +
                                "requestState ENUM('CLAIMED','UNCLAIMED','FINISHED', 'ORPHANED') NOT NULL" +
                                ");");
            }
            catch (SQLException sqlException)
            {
                log.logError("Unable to create SQL tables!");
                sqlException.printStackTrace();
            }
    }

    /*

requestID = int auto
requesterName = char(16)
requesterUUID = char(36)
worldUUID = char(36)
xLocation = int
yLocation = int
zLocation = int
requestMessage = VARCHAR(512
taskOwnerUUID = char(36)
taskOwnerName = char(16)
taskResolution = TEXT
timestamp = DATETIME
requestState = ENUM('CLAIMED','UNCLAIMED','FINISHED', 'ORPHANED')
     */
}
