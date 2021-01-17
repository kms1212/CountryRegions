package com.kms1212.mcplugin.countryregions;

import org.bukkit.plugin.java.JavaPlugin;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class CountryRegions extends JavaPlugin {
    private Command commandHandler;
    private UserAction actionHandler;
    private DataControl dataHandler;

    // Getter/Setter
    public Command getCommandHandler() {
        return commandHandler;
    }

    public UserAction getActionHandler() {
        return actionHandler;
    }

    // Methods
    @Override
    public void onEnable() {
        // Plugin startup logic
        // Loads configuration
        saveDefaultConfig();
        reloadConfig();

        dataHandler = new DataControl(this);
        dataHandler.initializeTables();

        // Registers command handler
        commandHandler = new Command(this);

        getCommand("cr").setExecutor(commandHandler);
        getCommand("cr").setTabCompleter(commandHandler);

        // Registers Event handler
        actionHandler = new UserAction(this);

        getServer().getPluginManager().registerEvents(actionHandler, this);

        // etc
        Date now = new Date();
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        getLogger().info("Initialization completed at " + format.format(now)  + ".");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        dataHandler.close();
    }
}
