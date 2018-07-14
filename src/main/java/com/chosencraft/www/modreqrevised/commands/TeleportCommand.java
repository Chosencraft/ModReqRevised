package com.chosencraft.www.modreqrevised.commands;

import com.chosencraft.purefocus.Chat;
import com.chosencraft.www.modreqrevised.Cache;
import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.Permissions;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand extends Command
{
    @Override
    public boolean passthrough(CommandSender commandSender, String[] args) throws NumberFormatException
    {

        if (!commandSender.hasPermission(Permissions.PERM_COMMAND_TELEPORT))
        {
            // ignore silently
            return true;
        }
        if (args.length == 1)
        {
            commandSender.sendMessage(format(ChatColor.RED + "/modreq tp <id>"));
            return true;
        }
        if (!(commandSender instanceof Player))
        {
            commandSender.sendMessage(Chat.format(format("&c You cannot teleport as console...really?")));
            return true;
        }
        Player player = (Player) commandSender;

        if (args.length > 1)
        {
            int id = Integer.parseInt(args[1]);

            ModReq request = Cache.requests.get(id);

            if (request == null)
            {
                commandSender.sendMessage(ChatColor.RED + "That modreq does not exist!");
                return true;
            }

            else
            {
                Location location = request.getRequesterLocation();
                player.teleport(location);
                /// TODO: Triple formatting.... yeah this formatting crap is super messy, need to fix it.
                player.sendMessage(Chat.format(format(String.format("^bYou have been teleported to &6ModReq %d &bat &2%s &6[&2%d&6,&2%d&6,&2%d&6]",
                        request.getID(), location.getWorld().getName(),location.getBlockX(), location.getBlockY(), location.getBlockZ() ))));
                return true;
            }

        }

        else
        {
            commandSender.sendMessage(ChatColor.RED + "Please specify a request!");
            commandSender.sendMessage(ChatColor.RED + "/modreq tp <id>");
            return true;
        }

    }

}
