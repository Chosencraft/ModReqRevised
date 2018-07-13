package com.chosencraft.www.modreqrevised;

import com.chosencraft.www.modreqrevised.commands.*;
import com.chosencraft.www.modreqrevised.database.sql.Consumer;
import com.chosencraft.www.modreqrevised.database.sql.Database;
import com.chosencraft.www.modreqrevised.database.sql.MySQL;
import com.chosencraft.www.modreqrevised.database.sql.query.queries.PrepareTableQuery;
import com.chosencraft.www.modreqrevised.utils.Config;
import com.chosencraft.www.modreqrevised.utils.Logger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.UUID;

public class ModReqRevisedMain extends JavaPlugin
{

    public static Database database;
    public static Logger logger;
    public static Config config;

    public static UUID consoleUUID = UUID.fromString("00000000-0000-0000-0000-000000000001"); // console reserved uuid

    public void onEnable()
    {
        this.logger = new Logger(getLogger());

        saveDefaultConfig();

        config = new Config(this);
        configureSQL();
        setupTables();
        registerCommands();
    }

    public void onDisable()
    {
        logger.logInfo("Disabling ModReq!");
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

        try
        {
            // Reflection method to grab internal command map
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            // Allow the retrieved reflection to become mutable
            bukkitCommandMap.setAccessible(true);
            // Register a link to the internal command map
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            // register all the commands.
            commandMap.register("request", new RequestCommand("request"));
            commandMap.register("claim", new ClaimRequestCommand("claim"));
            commandMap.register("done", new DoneCommand("done"));
            commandMap.register("reopen", new ReopenCommand("reopen"));
            commandMap.register("check", new CheckCommand("check"));
        }
        catch (IllegalAccessException accessException)
        {
            logger.logError("Illegal Access Exception on command map reflection!");
            accessException.printStackTrace();
        }
        catch (NoSuchFieldException noFieldException)
        {
            logger.logError("No Field Exception on command map reflection!");
            noFieldException.printStackTrace();
        }

    }


    /**
     * Configure the SQL settings and instance a consumer
     */
    private void configureSQL()
    {
        // register DB connection
        this.database = new MySQL(Config.SQL_USER, Config.SQL_PASS, Config.SQL_DB, Config.SQL_HOST, Config.SQL_PORT);

        if (! (this.database.connect()))
        {
            logger.logError("Unable to connect to database!");
            this.getPluginLoader().disablePlugin(this);
        }
        else
        {
            logger.logInfo("SQL connection successfully established!");
            new Consumer(this, database);
        }
    }
}
