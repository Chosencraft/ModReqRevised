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
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


public class ClaimRequestCommand extends Command
{

    @Override
    public boolean passthrough(CommandSender commandSender, String[] args) throws NumberFormatException
    {
        if (!commandSender.hasPermission(Permissions.PERM_COMMAND_CLAIM))
        {
            // silent ignore
            return true;
        }
        if (args.length == 1)
        {
            commandSender.sendMessage(format(ChatColor.RED + "/modreq claim <id>"));
            return true;
        }

        if (args.length > 1)
        {
            int id = Integer.parseInt(args[1]);

            ModReq request = Cache.requests.get(id);
            if (request == null)
            {
                commandSender.sendMessage(Chat.format(prefix +  "&cThat modreq does not exist!"));
            }
            else
            {
                if (request.getState().equals(RequestState.UNCLAIMED))
                {
                    setOwner(request,commandSender);
                    Consumer.queue(new ClaimQuery(request));
                    commandSender.sendMessage(Chat.format(prefix + "&6You have claimed request " + id));
                    Cache.notify(Chat.format(String.format(prefix + "&d%s has claimed modreq # %d", commandSender.getName(),id)));
                }
                else if (request.getTaskOwner().equalsIgnoreCase(commandSender.getName()))
                {
                    setOwner(request, null);
                    Consumer.queue(new UnclaimQuery(request));
                    commandSender.sendMessage(Chat.format(prefix + "&6You have unclaimed request " + id));
                    Cache.notify(Chat.format(String.format(prefix + "&d%s has unclaimed modreq # %d", commandSender.getName(),id)));
                }

            }
        }
        return true;
    }

    /**
     * sets the owner of the request
     * @param request The request to be modified
     * @param commandSender Sets the owenr of the request, null is allowed for unclaiming
     */
    private void setOwner(ModReq request, CommandSender commandSender)
    {
        if (commandSender == null)
        {
            request.setTaskOwner(null, null);
        }
        else if (commandSender instanceof Player)
        {
            Player player = (Player) commandSender;
            request.setTaskOwner(player.getName(), player.getUniqueId());
        }
        else
        {
            request.setTaskOwner(commandSender.getName(), ModReqRevisedMain.consoleUUID);
        }
    }
}
