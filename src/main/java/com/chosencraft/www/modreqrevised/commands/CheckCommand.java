package com.chosencraft.www.modreqrevised.commands;

import com.chosencraft.purefocus.Chat;
import com.chosencraft.www.modreqrevised.Cache;
import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.Permissions;
import com.chosencraft.www.modreqrevised.utils.RequestState;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CheckCommand extends Command
{

    @Override
    public boolean passthrough(CommandSender commandSender, String[] args) throws NumberFormatException
    {
        if (!commandSender.hasPermission(Permissions.PERM_COMMAND_CHECK))
        {
            // no responses
            return true;
        }

        if (args.length == 1)
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
            if (!requests.isEmpty())
            {
                Iterator<ModReq> iterator = requests.iterator();
                System.out.println(requests.toString());

                for (ModReq request : requestValues)
                {
                    commandSender.sendMessage(request.getFormattedSummary());
                    requests.add(request); // This really doesn't seem needed at all
                }
            }
        }
        else
        {
            int id = Integer.parseInt(args[1]);



            commandSender.sendMessage(Chat.format("&b-------- Checking #" + id + " --------"));
            ModReq request = Cache.requests.get(id);
            if (request == null)
            {
                commandSender.sendMessage("That modreq does not exist!");
            }
            else
            {
                sendInfoMessage(commandSender,request);
            }
        }
        return false;
    }


    /**
     * Sends a formatted info message about the request
     * @param commandSender Who to send the message to
     * @param request The request to send info about
     */
    private void sendInfoMessage(CommandSender commandSender, ModReq request)
    {
        // Req Number [ PlayerOnline | Player Offline ]
        Player player = Bukkit.getPlayer(request.getRequesterUUID());
        // I could use a ternary op here, but it'd just be unreadable and ugly
        if (player != null && player.isOnline())
        {
            commandSender.sendMessage(Chat.format(String.format("&4%d &6[&b&s&6] &4%s", request.getID(), player.getName(), request.getState().toString())));
        }
        else
        {
            commandSender.sendMessage(Chat.format(String.format("&4%d &6[&c&s&6] &4%s", request.getID(), player.getName(), request.getState().toString())));
        }
        // Request Message
        commandSender.sendMessage(Chat.format(String.format("&6Request Message: &a%s", request.getRequestMessage())));

        // World Name, x , y ,z
        Location location = request.getRequesterLocation();
        commandSender.sendMessage(Chat.format(String.format("&6Location: &2%s, &6[&2%d&6,&2%d&6,&2%d&6]", location.getWorld().getName(), location.getBlockX(), location.getBlockY(), location.getBlockZ())));
        // timestamp
        commandSender.sendMessage(Chat.format(String.format("&6Time request was made: &5&s", convertToEST(request.getTimestamp()))));
        // Task owner
        if (request.getTaskOwner() != null)
        {
            commandSender.sendMessage(Chat.format(String.format("&6Task Owner: &ds", Bukkit.getPlayer(request.getTaskOwnerUUID()).getName())));
        }
        // Task resolution
        if (request.getTaskResolution() != null)
        {
            commandSender.sendMessage(Chat.format(String.format("&6Task Resolution: &5%s", request.getTaskResolution())));
        }
    }

    private String convertToEST(Timestamp timestamp)
    {
        TimeZone timeZone = TimeZone.getTimeZone("EST");
        long utc = timestamp.getTime();
        Date utcHolder = new Date(utc);
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a z");

        return String.format(format, utcHolder);
    }
}
