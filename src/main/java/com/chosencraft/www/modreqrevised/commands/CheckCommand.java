package com.chosencraft.www.modreqrevised.commands;

import com.chosencraft.purefocus.Chat;
import com.chosencraft.www.modreqrevised.Cache;
import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.utils.RequestState;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CheckCommand implements CommandExecutor
{

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args)
    {
        if (args.length == 0)
        {
            Collection<ModReq> requestValues = Cache.requests.values();
            List<ModReq> requests = new ArrayList<>();

            for (ModReq request : requestValues)
            {
                if (request.getState().equals(RequestState.UNCLAIMED) || request.getState().equals(RequestState.CLAIMED))
                {
                    requests.add(request);
                }
            }

            commandSender.sendMessage(Chat.format("&b-------- There are " + requests.size() + " requests --------"));
            for (ModReq request : requests)
            {
                if (request.getState().equals(RequestState.UNCLAIMED) || request.getState().equals(RequestState.CLAIMED))
                {
                    commandSender.sendMessage(request.getFormattedSummary());
                    requests.add(request); // This really doesn't seem needed at all

                }
            }
        }
        else
        {
            int id;
            try
            {
                id = Integer.parseInt(args[0]);
            }
            catch (NumberFormatException formatException)
            {
                commandSender.sendMessage(ChatColor.RED + "That is not a valid ID!");
                return true;
            }

            commandSender.sendMessage(Chat.format("&b-------- Checking #" + id + " --------"));
            ModReq request = Cache.requests.get(id);
            if (request == null)
            {
                commandSender.sendMessage("That modreq does not exist!");
            }
            else
            {
                commandSender.sendMessage(request.getRequestMessage());
            }
        }
        return false;
    }
}
