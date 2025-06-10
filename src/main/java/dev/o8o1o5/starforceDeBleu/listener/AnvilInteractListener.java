package dev.o8o1o5.starforceDeBleu.listener;

import dev.o8o1o5.starforceDeBleu.manager.GuiManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;

public class AnvilInteractListener implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getClickedBlock().getType() == Material.ANVIL) {
            if (player.isSneaking()) {
                event.setCancelled(true);

                player.openInventory(GuiManager.getStarforceGUI());
            }
        }
    }
}
