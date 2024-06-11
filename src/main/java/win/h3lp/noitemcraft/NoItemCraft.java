package win.h3lp.noitemcraft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public final class NoItemCraft extends JavaPlugin implements TabExecutor {

    private Set<Material> prohibitedItems = new HashSet<>();
    private Map<String, String> messages = new HashMap<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        loadProhibitedItems();
        loadMessages();
        getLogger().info("NoItemCraft 1.0 has been enabled!");
        registerEvents(new CraftListener(prohibitedItems, messages));
        getCommand("noitemcraft").setExecutor(this);
    }

    private void loadProhibitedItems() {
        FileConfiguration config = getConfig();
        List<String> items = config.getStringList("prohibited-items");
        for (String item : items) {
            try {
                Material material = Material.valueOf(item.toUpperCase());
                prohibitedItems.add(material);
            } catch (IllegalArgumentException e) {
                getLogger().warning("Invalid material in config: " + item);
            }
        }
    }

    private void loadMessages() {
        FileConfiguration config = getConfig();
        messages.put("item_blocked", config.getString("messages.item_blocked", "You cannot craft this item. :)"));
        messages.put("item_already_blocked", config.getString("messages.item_already_blocked", "Item {item} is already blocked."));
        messages.put("item_block_success", config.getString("messages.item_block_success", "Item {item} has been blocked."));
        messages.put("item_unblock_success", config.getString("messages.item_unblock_success", "Item {item} has been unblocked."));
        messages.put("item_not_blocked", config.getString("messages.item_not_blocked", "Item {item} is not blocked."));
        messages.put("invalid_item", config.getString("messages.invalid_item", "Invalid item: {item}."));
        messages.put("command_usage", config.getString("messages.command_usage", "Usage: /noitemcraft <block|unblock> <item>"));
    }

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("noitemcraft")) {
            if (args.length != 2) {
                sender.sendMessage(messages.get("command_usage"));
                return true;
            }

            String action = args[0].toLowerCase();
            String itemName = args[1].toUpperCase();
            try {
                Material material = Material.valueOf(itemName);

                if (action.equals("block")) {
                    if (prohibitedItems.add(material)) {
                        FileConfiguration config = getConfig();
                        List<String> items = config.getStringList("prohibited-items");
                        items.add(itemName);
                        config.set("prohibited-items", items);
                        saveConfig();
                        sender.sendMessage(messages.get("item_block_success").replace("{item}", itemName));
                    } else {
                        sender.sendMessage(messages.get("item_already_blocked").replace("{item}", itemName));
                    }
                } else if (action.equals("unblock")) {
                    if (prohibitedItems.remove(material)) {
                        FileConfiguration config = getConfig();
                        List<String> items = config.getStringList("prohibited-items");
                        items.remove(itemName);
                        config.set("prohibited-items", items);
                        saveConfig();
                        sender.sendMessage(messages.get("item_unblock_success").replace("{item}", itemName));
                    } else {
                        sender.sendMessage(messages.get("item_not_blocked").replace("{item}", itemName));
                    }
                } else {
                    sender.sendMessage(messages.get("command_usage"));
                }
            } catch (IllegalArgumentException e) {
                sender.sendMessage(messages.get("invalid_item").replace("{item}", itemName));
            }
            return true;
        }
        return false;
    }

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (command.getName().equalsIgnoreCase("noitemcraft")) {
            if (args.length == 1) {
                return Arrays.asList("block", "unblock");
            } else if (args.length == 2) {
                List<String> completions = new ArrayList<>();
                for (Material material : Material.values()) {
                    completions.add(material.name());
                }
                return completions;
            }
        }
        return null;
    }
}
