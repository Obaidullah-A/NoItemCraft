package win.h3lp.noitemcraft;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;

import java.util.Map;
import java.util.Set;

public class CraftListener implements Listener {

    private final Set<Material> prohibitedItems;
    private final Map<String, String> messages;

    public CraftListener(Set<Material> prohibitedItems, Map<String, String> messages) {
        this.prohibitedItems = prohibitedItems;
        this.messages = messages;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCraft(CraftItemEvent event) {
        if (prohibitedItems.contains(event.getRecipe().getResult().getType())) {
            event.setCancelled(true);
            if (event.getWhoClicked() instanceof Player) {
                Player player = (Player) event.getWhoClicked();
                player.sendMessage(ChatColor.RED + messages.get("item_blocked"));
            }
        }
    }
}
