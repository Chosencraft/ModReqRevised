package com.chosencraft.www.modreqrevised.commands;

import com.chosencraft.purefocus.Chat;
import com.chosencraft.www.modreqrevised.Permissions;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

public class ModReqCommand extends BukkitCommand
{

    public ModReqCommand(String commandName)
    {
        super(commandName);

    }
    @Override
    public boolean execute(CommandSender commandSender, String commandLabel, String[] args)
    {
        if (!commandSender.hasPermission(Permissions.PERM_COMMAND_USE))
        {
            // fail silenty
            return true;
        }
        if (args.length != 0)
        {
            try
            {
                switch (args[0].toLowerCase())
                {
                    case "check":
                        return new CheckCommand().passthrough(commandSender, args);
                    case "claim":
                        return new ClaimRequestCommand().passthrough(commandSender, args);
                    case "done":
                        return new DoneCommand().passthrough(commandSender, args);
                    case "reopen":
                        return new ReopenCommand().passthrough(commandSender, args);
                    case "tp":
                        return new TeleportCommand().passthrough(commandSender, args);
                    default:
                        printHelpMessage(commandSender);
                        break;
                }
            }
            catch (NumberFormatException formatException)
            {
                commandSender.sendMessage(ChatColor.RED + "That is not a valid ID!");
            }
        }
        return true;
    }

    private void printHelpMessage(CommandSender commandSender)
    {
        commandSender.sendMessage(Chat.center(ChatColor.GOLD + "[" + ChatColor.AQUA + "ModReq" + ChatColor.GOLD + "]"));
        commandSender.sendMessage(Chat.format(String.format("&6%s -> &b%s", "/modreq", "Displays this help menu")));
        commandSender.sendMessage(Chat.format(String.format("&6%s -> &b%s", "/modreq check [id]", "Checks for unclaimed modreqs, and detailed info if an ID is specified, only supports reqs in cache at the time.")));
        commandSender.sendMessage(Chat.format(String.format("&6%s -> &b%s", "/modreq claim <id>", "Toggles claim of a modreq with ID  <id>.")));
        commandSender.sendMessage(Chat.format(String.format("&6%s -> &b%s", "/modreq done <id> [Resolution]", "Finishes a command, optionally you can also add a resolution note to the task. &4(SUGGESTED)")));
        commandSender.sendMessage(Chat.format(String.format("&6%s -> &b%s", "/modreq reopen <id>", "Reopens an older moderator request")));
        commandSender.sendMessage(Chat.format(String.format("&6%s -> &b%s", "/modreq tp <id>", "Teleports to the location where the modreq was requested.")));
    }
}
