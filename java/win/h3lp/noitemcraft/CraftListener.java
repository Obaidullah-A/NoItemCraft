package win.h3lp.noitemcraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import java.util.Set;

public class CraftListener implements Listener {

    private final Set<Material> prohibitedItems;

    public CraftListener(Set<Material> prohibitedItems) {
        this.prohibitedItems = prohibitedItems;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCraft(CraftItemEvent event) {
        if (prohibitedItems.contains(event.getRecipe().getResult().getType())) {
            event.setCancelled(true);
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                player.sendMessage(ChatColor.RED + "You cannot craft this item. :)");
            }
        }
    }
}
