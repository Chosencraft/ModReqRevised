package com.chosencraft.www.modreqrevised;

import com.chosencraft.www.modreqrevised.utils.Logger;
import org.bukkit.plugin.java.JavaPlugin;

public class ModReqRevisedMain extends JavaPlugin
{

    public static Logger logger;

    public void onEnable()
    {
        this.logger = new Logger(getLogger());

        saveDefaultConfig();
    }

    public void onDisable()
    {

    }

}
