package com.chosencraft.www.modreqrevised.commands;

import com.chosencraft.www.modreqrevised.Cache;
import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.Permissions;
import com.chosencraft.www.modreqrevised.database.sql.Consumer;
import com.chosencraft.www.modreqrevised.database.sql.query.queries.ReopenQuery;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;

public class ReopenCommand extends Command
{

    @Override
    public boolean passthrough(CommandSender commandSender, String[] args) throws NumberFormatException
    {
        if (!commandSender.hasPermission(Permissions.PERM_COMMAND_REOPEN))
        {
            // ignore silently
            return true;
        }

        if (args.length == 1)
        {
            commandSender.sendMessage(format(ChatColor.RED + "/modreq reopen <id> "));
            return true;
        }

        if (args.length > 1 )
        {
            int id = Integer.parseInt(args[1]);

            ModReq request = Cache.requests.get(id);
            if (request == null)
            {
                commandSender.sendMessage(ChatColor.RED + "That modreq does not exist!");
                return true;
            }
            StringBuilder builder = new StringBuilder();

            for (int i = 2; i < args.length ; i++)
            {
                builder.append(args[i]).append(" ");
            }
            request.reopenTask();
            Cache.notify(ChatColor.RED + String.format("%s has reopened modreq #%d.", commandSender.getName(), request.getID() ));
            Consumer.queue(new ReopenQuery(request));
            return true;

        }
        else
        {
            commandSender.sendMessage(ChatColor.RED + "Please specify a request to reopen!");
            return true;
        }

    }
}
