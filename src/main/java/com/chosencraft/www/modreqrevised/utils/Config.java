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
    public static String SQL_TABLE_NAME;

    public Config (Plugin plugin)
    {
        FileConfiguration config = plugin.getConfig();

        this.SQL_USER = config.getString("database.mysql.username");
        this.SQL_PASS = config.getString("database.mysql.password");
        this.SQL_DB = config.getString("database.mysql.database");
        this.SQL_HOST = config.getString("database.mysql.host");
        this.SQL_PORT = config.getInt("database.mysql.port");
        this.SQL_TABLE_NAME = config.getString("database.mysql.table_name");
    }
}
