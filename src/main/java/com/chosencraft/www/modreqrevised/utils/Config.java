package com.chosencraft.www.modreqrevised.utils;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

public class Config
{
    public static String SQL_USER;
    public static String SQL_PASS;
    public static String SQL_DB;
    public static String SQL_HOST;
    public static int SQL_PORT;

    private Config (Plugin plugin)
    {
        FileConfiguration config = plugin.getConfig();

        this.SQL_USER = config.getString("database.username");
        this.SQL_PASS = config.getString("database.password");
        this.SQL_DB = config.getString("database.database");
        this.SQL_HOST = config.getString("database.host");
        this.SQL_PORT = config.getInt("database.port");

    }
}
