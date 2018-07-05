package com.chosencraft.www.modreqrevised;

import com.chosencraft.www.modreqrevised.commands.ClaimRequestCommand;
import com.chosencraft.www.modreqrevised.commands.RequestCommand;
import com.chosencraft.www.modreqrevised.database.sql.Database;
import com.chosencraft.www.modreqrevised.database.sql.MySQL;
import com.chosencraft.www.modreqrevised.database.sql.query.queries.PrepareTableQuery;
import com.chosencraft.www.modreqrevised.utils.Config;
import com.chosencraft.www.modreqrevised.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class ModReqRevisedMain extends JavaPlugin
{

    public static Database database;
    public static Logger logger;

    public static UUID consoleUUID = UUID.fromString("00000000-0000-0000-0000-000000000001"); // console reserved uuid

    public void onEnable()
    {
        this.logger = new Logger(getLogger());

        saveDefaultConfig();

        this.database = new MySQL(Config.SQL_USER, Config.SQL_PASS,Config.SQL_DB,Config.SQL_HOST,Config.SQL_PORT);
        if (!(this.database.connect()))
        {
         logger.logError("Unable to connect to database!");
        }

        registerCommands();
    }

    public void onDisable()
    {

    }

    /**
     * Creates the needed tables if they don't exist
     */
    private void setupTables()
    {
        new PrepareTableQuery();
    }

    /**
     * Registers all commands to bukkit API
     */
    private void registerCommands()
    {
        getCommand("request").setExecutor(new RequestCommand());
        getCommand("claim").setExecutor(new ClaimRequestCommand());
    }
}
