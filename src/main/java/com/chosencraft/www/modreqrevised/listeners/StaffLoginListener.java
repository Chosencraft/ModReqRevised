package com.chosencraft.www.modreqrevised.listeners;

import com.chosencraft.www.modreqrevised.Cache;
import com.chosencraft.www.modreqrevised.Permissions;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class StaffLoginListener implements Listener
{

    @EventHandler
    public void onLogin(PlayerJoinEvent joinEvent)
    {
        Player player = joinEvent.getPlayer();

        if (player.hasPermission(Permissions.PERM_UNCLAIMED_NOTIFY))
        {
            Cache.notifies.add(player);
        }
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent quitEvent)
    {
        Player player = quitEvent.getPlayer();
        if (player.hasPermission(Permissions.PERM_UNCLAIMED_NOTIFY) && Cache.notifies.contains(player))
        {
            Cache.notifies.remove(player);
        }

    }
}
