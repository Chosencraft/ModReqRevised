package com.chosencraft.www.modreqrevised.commands;

import com.chosencraft.purefocus.Chat;
import com.chosencraft.www.modreqrevised.Cache;
import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.ModReqRevisedMain;
import com.chosencraft.www.modreqrevised.Permissions;
import com.chosencraft.www.modreqrevised.database.sql.Consumer;
import com.chosencraft.www.modreqrevised.database.sql.query.queries.ClaimQuery;
import com.chosencraft.www.modreqrevised.database.sql.query.queries.UnclaimQuery;
import com.chosencraft.www.modreqrevised.utils.RequestState;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ClaimRequestCommand implements CommandExecutor
{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (!sender.hasPermission(Permissions.PERM_COMMAND_CLAIM))
        {
            // silent ignore
            return true;
        }

        if (args.length > 0)
        {
            int id = -1; // all reqs are positive, so this will never exist
            try
            {
                id = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException formatException)
            {
                sender.sendMessage(Chat.format("&cThat is not a number!"));
            }
            ModReq request = Cache.requests.get(id);
            if (request == null)
            {
                sender.sendMessage(Chat.format("&cThat modreq does not exist!"));
            }
            else
            {
                if (request.getState().equals(RequestState.UNCLAIMED))
                {
                    setOwner(request,sender);
                    Consumer.queue(new ClaimQuery(request));
                    sender.sendMessage(Chat.format("&6You have claimed request " + id));
                    Cache.notify(Chat.format(String.format("&d%s has claimed modreq # %d", sender.getName(),id)));
                }
                else if (request.getTaskOwner().equalsIgnoreCase(sender.getName()))
                {
                    setOwner(request, null);
                    Consumer.queue(new UnclaimQuery(request));
                    sender.sendMessage(Chat.format("&6You have unclaimed request " + id));
                    Cache.notify(Chat.format(String.format("&d%s has unclaimed modreq # %d", sender.getName(),id)));
                }

            }
        }
        return true;
    }

    /**
     * sets the owner of the request
     * @param request The request to be modified
     * @param sender Sets the owenr of the request, null is allowed for unclaiming
     */
    private void setOwner(ModReq request, CommandSender sender)
    {
        if (sender == null)
        {
            request.setTaskOwner(null, null);
        }
        else if (sender instanceof Player)
        {
            Player player = (Player) sender;
            request.setTaskOwner(player.getName(), player.getUniqueId());
        }
        else
        {
            request.setTaskOwner(sender.getName(), ModReqRevisedMain.consoleUUID);
        }
    }
}
