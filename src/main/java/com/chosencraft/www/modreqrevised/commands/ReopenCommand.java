package com.chosencraft.www.modreqrevised.commands;

import com.chosencraft.www.modreqrevised.Cache;
import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.Permissions;
import com.chosencraft.www.modreqrevised.database.sql.Consumer;
import com.chosencraft.www.modreqrevised.database.sql.query.queries.ReopenQuery;
import com.chosencraft.www.modreqrevised.database.sql.query.queries.UpdateCacheQuery;
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
                commandSender.sendMessage(ChatColor.RED + "Pulling modreq from database, this may take a moment");
            }
            try
            {
                Consumer.queue(new ReopenQuery(id));
                Consumer.queue(new UpdateCacheQuery());
                Cache.notify(ChatColor.RED + String.format("%s has reopened modreq #%d.", commandSender.getName(), request.getID()));
            }
            catch (NullPointerException nullPointerException)
            {
                /* This may sound absolutely insane, but this is the current "fix"
                 * until i actually fix the issue
                 *
                 * The issues is that the two queries don't respond fast enough before request.getID is called,
                 * I'll need to handle a wait timer on it to fix it, Later :tm:
                 */
            }
            return true;

        }
        else
        {
            commandSender.sendMessage(ChatColor.RED + "Please specify a request to reopen!");
            return true;
        }

    }
}
