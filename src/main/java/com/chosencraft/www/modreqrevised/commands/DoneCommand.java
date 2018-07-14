package com.chosencraft.www.modreqrevised.commands;

import com.chosencraft.www.modreqrevised.Cache;
import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.ModReqRevisedMain;
import com.chosencraft.www.modreqrevised.Permissions;
import com.chosencraft.www.modreqrevised.database.sql.Consumer;
import com.chosencraft.www.modreqrevised.database.sql.query.queries.DoneQuery;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class DoneCommand extends Command
{

    @Override
    public boolean passthrough(CommandSender commandSender, String[] args) throws NumberFormatException
    {

        if (!commandSender.hasPermission(Permissions.PERM_COMMAND_FINISH))
        {
            // ignore silently
            return true;
        }
        if (args.length == 1)
        {
            commandSender.sendMessage(format(ChatColor.RED + "/modreq done <id>  [Resolution Message]"));
            return true;
        }

        if (args.length > 1)
        {
            int id = Integer.parseInt(args[1]);

            ModReq request = Cache.requests.get(id);

            if (request == null)
            {
                commandSender.sendMessage(ChatColor.RED + "That modreq does not exist!");
                return true;
            }

            Player taskOwner = Bukkit.getPlayer(commandSender.getName());
            Player requester = Bukkit.getPlayer(request.getRequesterUUID());
            UUID   taskOwnerUUID;

            if (requester != null)
            {
                requester.sendMessage(ChatColor.GREEN + String.format("%s finished your modreq #%d!", commandSender.getName(), request.getID()));
            }
            // check is player or console
            if (taskOwner == null)
            {
                taskOwnerUUID = ModReqRevisedMain.consoleUUID;
            }
            else
            {
                taskOwnerUUID = taskOwner.getUniqueId();
            }

            // string together resolution if it exists
            StringBuilder resolution = new StringBuilder();
            if (args.length > 2)
            {
                for (int i = 2; i < args.length ; i++)
                {
                    resolution.append(args[i]).append(" ");
                }

            }

            Cache.notify(ChatColor.GREEN + String.format("%s finished modreq %d.", commandSender.getName(), request.getID()) );
            request.setTaskOwner(commandSender.getName(), taskOwnerUUID);
            request.completeTask(resolution.toString());
            Consumer.queue(new DoneQuery(request));
            return true;

        }

        else
        {
            commandSender.sendMessage(ChatColor.RED + "Please specify a request!");
            commandSender.sendMessage(ChatColor.RED + "/done <id> [resolution]");
            return true;
        }
    }
}
