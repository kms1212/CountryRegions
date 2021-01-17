package com.kms1212.mcplugin.countryregions;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Command implements CommandExecutor, TabCompleter {
    private CountryRegions plugin;

    public Command(CountryRegions plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args) {
        if (args.length != 0)
        {
            if (args[0].equalsIgnoreCase("help") && args.length == 1) {
                sender.sendMessage("[CountryRegions] Help Page");
                return true;
            } else if (args[0].equalsIgnoreCase("reload") && args.length == 1) {
                sender.sendMessage("[CountryRegions] Reloading ServerInfo config...");
                plugin.onDisable();
                plugin.onEnable();
                sender.sendMessage("[CountryRegions] Reloaded!");
                return true;
            } else if (args[0].equalsIgnoreCase("config") && args.length > 1 && args.length < 3) {
                boolean isArgValid = false;
                Set<String> configSet = plugin.getConfig().getConfigurationSection("").getKeys(true);
                List<String> targetList = new ArrayList<>(configSet);

                if (args.length == 3) {
                    for (int i = 0; i < targetList.size(); i++) {
                        if (targetList.get(i).contains(".")) {
                            isArgValid = args[1].equals(targetList.get(i));
                            if (isArgValid) {
                                break;
                            }
                        }
                    }

                    if (isArgValid) {
                        sender.sendMessage("[CountryRegions] " +
                                String.format("Value changed: %s > %s",
                                        plugin.getConfig().get(args[1]).toString(), args[2]));
                        plugin.getLogger().info(String.format("Value changed: %s > %s",
                                plugin.getConfig().get(args[1]).toString(), args[2]));

                        plugin.getConfig().set(args[1], args[2]);
                        plugin.saveConfig();
                    }
                    return isArgValid;
                } else if (args.length == 2) {
                    for (int i = 0; i < targetList.size(); i++) {
                        if (targetList.get(i).contains(".")) {
                            isArgValid = args[1].equals(targetList.get(i));
                            if (isArgValid) {
                                break;
                            }
                        }
                    }

                    if (isArgValid) {
                        sender.sendMessage("[CountryRegions] " +
                                String.format("%s: %s", args[1], plugin.getConfig().get(args[1]).toString()));
                    }
                    return isArgValid;
                }
            } else if (args[0].equalsIgnoreCase("setregion") && args.length == 2) {
                sender.sendMessage("[CountryRegions] Right click to set pos1 for region " + args[1] + ".");
                return true;
            } else if (args[0].equalsIgnoreCase("togglewand") && args.length == 1) {
                boolean current = plugin.getActionHandler().getWandStatus();
                if (current)
                    sender.sendMessage("[CountryRegions] Wand disabled.");
                else
                    sender.sendMessage("[CountryRegions] Wand enabled.");
                plugin.getActionHandler().setWandStatus(!current);
                return true;
            } else if (args[0].equalsIgnoreCase("getwand") && args.length == 1) {
                sender.sendMessage("[CountryRegions] Wand is added to your inventory");
                ((Player)sender).getInventory().addItem(new ItemStack(Material.STONE_SHOVEL));
                return true;
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String alias, String[] args) {
        List<String> list = new ArrayList<>();
        list.clear();
        if (args.length == 1) {
            list.add("help");
            list.add("reload");
            list.add("config");
            list.add("setregion");
            list.add("togglewand");
            list.add("getwand");
        } else if (args.length == 2) {
            if (args[0].equalsIgnoreCase("config")) {
                Set<String> configSet = plugin.getConfig().getConfigurationSection("").getKeys(true);
                List<String> targetList = new ArrayList<>(configSet);

                for (int i = 0; i < targetList.size(); i++) {
                    if (targetList.get(i).contains(".")) {
                        list.add(targetList.get(i));
                    }
                }
            }
        }

        if (args.length < 3) {
            List<String> ret = new ArrayList<>();
            for (String a : list) {
                if (a.toLowerCase().startsWith(args[args.length - 1].toLowerCase()))
                    ret.add(a);
            }
            return ret;
        }


        return null;
    }
}
