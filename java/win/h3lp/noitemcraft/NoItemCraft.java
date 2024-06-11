package win.h3lp.noitemcraft;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public final class NoItemCraft extends JavaPlugin {

    private Set<Material> prohibitedItems = new HashSet<>();

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        loadProhibitedItems();
        getLogger().info("NoItemCraft 1.0 has been enabled!");
        registerEvents(new CraftListener(prohibitedItems));
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

    private void registerEvents(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getServer().getPluginManager().registerEvents(listener, this);
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
