package com.chosencraft.www.modreqrevised;

import com.chosencraft.www.modreqrevised.database.sql.Consumer;
import com.chosencraft.www.modreqrevised.database.sql.query.queries.UpdateCacheQuery;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.LinkedList;

public class Cache
{

    public static LinkedList<Player> notifies = new LinkedList<Player>();

    public static HashMap<Integer, ModReq> requests = new HashMap<Integer, ModReq>();


    /**
     * Adds the request to cache
     * @param request Request to be added
     */
    public static void addModReq(ModReq request)
    {
        requests.put(request.getID(), request);
    }

    /**
     * Removes request from cache
     * @param requestID ID of request
     */
    public static void removeModReq(int requestID)
    {
        requests.remove(requestID);
    }

    /**
     * Removes all ModReqs from requests cache
     */
    public static void clearRequests()
    {
        requests.clear();
    }
    /**
     *
     * Notifies notifiees with a message
     *
     * @param message The message to notify with
     */
    public static void notify(String message)
    {
       for (Player player :  notifies)
       {
           player.sendMessage(message);
       }
    }

    public static void startAutoSync(Plugin plugin)
    {
        Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, new Runnable() {
            @Override
            public void run()
            {
                Consumer.queue(new UpdateCacheQuery());
            }
        }, 3000, 3000);
    }

}
