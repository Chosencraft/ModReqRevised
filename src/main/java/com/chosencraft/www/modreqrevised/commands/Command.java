package com.chosencraft.www.modreqrevised.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public abstract class Command
{

    protected String prefix = ChatColor.GOLD + "[" + ChatColor.AQUA + "ModReq" + ChatColor.GOLD + "]";
    protected String format = "%s %s";

    protected String format(String message)
    {
        return String.format(format, prefix, message);
    }

    public abstract boolean passthrough(CommandSender commandSender, String[] args) throws NumberFormatException;

}
