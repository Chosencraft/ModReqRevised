package com.chosencraft.www.modreqrevised.utils;

import net.md_5.bungee.api.ChatColor;

import java.util.logging.Level;

public class Logger
{
    private java.util.logging.Logger logger;

    private String logMessage =
            ChatColor.YELLOW + "[" +
                    ChatColor.AQUA + "ModReq" +
                    ChatColor.YELLOW + "]" +
                    ChatColor.RED +  " [%s]" +
                    ChatColor.AQUA  + " %s" +
                    ChatColor.RESET;

    public Logger(java.util.logging.Logger logger)
    {

        this.logger = logger;
        logger.setLevel(Level.ALL);
    }


    public void logWarning(String... message)
    {
        for (String line : message)
        {
            logger.log(Level.WARNING, String.format(logMessage, "WARNING", line));
        }
    }

    public void logError(String... message)
    {
        for (String line : message)
        {
            logger.log(Level.SEVERE, String.format(logMessage, "ERROR", line));
        }
    }

    public void logInfo(String... message)
    {
        for (String line : message)
        {
            logger.log(Level.INFO, String.format(logMessage, "INFO", line));
        }
    }
}
