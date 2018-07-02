package com.chosencraft.www.modreqrevised.tasks;

import com.chosencraft.www.modreq.ModReq;
import com.chosencraft.www.modreq.Permissions;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.LinkedList;

/**
 * Sends an occasional notification to the staff if reqs are unclaimed
 */
public class AutoNotifyStaff implements Runnable
{
    private static String precedingMessage = ChatColor.BLUE + "There are currently " + ChatColor.RED + "%i" + ChatColor.BLUE + " unclaimed requests outstanding!";
    // [reqID] [playername] message                          req, pname, msg
    private static String format = ChatColor.BLUE + "[ " + ChatColor.RED + "%i" + ChatColor.BLUE +" ]" +
            ChatColor.GOLD + "[ " + ChatColor.AQUA + "%s" + ChatColor.GOLD + " ] " +
            ChatColor.GREEN + "%s";

    @Override
    public void run()
    {
        executeServerNotifier();
    }


    public static void executeServerNotifier()
    {
        LinkedList<ModReq> reqs = ModReqCalls.getUnclaimedModReqs();

        if (reqs.size() != 0)
        {
            for (Player player : Bukkit.getServer().getOnlinePlayers())
            {
                if (player.hasPermission(Permissions.PERM_UNCLAIMED_NOTIFY))
                {
                    executeSingleNotifier(player);
                }
            }
        }
    }

    public static void executeSingleNotifier(Player player)
    {
        LinkedList<ModReq> reqs = ModReqCalls.getUnclaimedModReqs();
        if (reqs.size() != 0)
        {
            player.sendMessage(String.format(precedingMessage, reqs.size()));
            for (ModReq modReq : reqs)
            {
                player.sendMessage(String.format(format,  modReq.getID(), modReq.getRequester(), modReq.getRequestMessage()));
            }
        }

    }
}
