package com.chosencraft.www.modreqrevised.commands;

import com.chosencraft.purefocus.Chat;
import com.chosencraft.www.modreqrevised.ModReq;
import com.chosencraft.www.modreqrevised.database.sql.query.queries.NewModReqQuery;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.WeakHashMap;

public class RequestCommand implements CommandExecutor
{
    private Map<Player, Integer> sentReqs = new WeakHashMap<>();
    private Map<Player, String>  issuedReqs = new WeakHashMap<>();
    private long timestamp = 0;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (System.currentTimeMillis() - timestamp > 0x124F80L)
        {
            timestamp = System.currentTimeMillis();
            issuedReqs.clear();
        }
        if (!(sender instanceof Player))
        {
            sender.sendMessage(Chat.format("Unable to make a request from console!"));
            return true;
        }

        Player player = (Player) sender;
        int count = 0;

        if (sentReqs.containsKey(player))
        {
            count = sentReqs.get(player);
        }

        if (count < 3 )
        {
            sentReqs.put(player, ++count);

            if (args.length >= 3)
            {
                StringBuilder request = new StringBuilder();
                for (String word : args)
                {
                    request.append(word).append(" ");
                }

                String msg = request.toString().trim();

                if (issuedReqs.containsKey(player) && issuedReqs.get(player).equalsIgnoreCase(msg))
                {
                    sender.sendMessage(Chat.format("&cYou can't send in duplicate moderator requests!"));
                }
                else
                {
                    issuedReqs.put(player,msg);
                    Location location = player.getLocation();
                    ModReq modReq = new ModReq(
                            player.getName(),
                            player.getUniqueId(),
                            location.getWorld().getUID(),
                            // Considering changing this to just use location and parse out in constructor
                            location.getBlockX(),
                            location.getBlockY(),
                            location.getBlockZ(),
                            msg
                    );
                    new NewModReqQuery(modReq);
                    //TODO: probably also add to cache here? appears not to be a thing in pures, but could be an oddity

                }

            }
            else
            {
                sender.sendMessage(Chat.format("&cYou must use at least 3 words to submit a moderator RequestCommand!"));
            }
        }
        else
        {
            sender.sendMessage(Chat.format("&cYou have already send in too many moderator requests! " +
                    "Please wait and a staff member should be with you shortly!"));
        }

        return true;
    }
}
