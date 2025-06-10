package dev.o8o1o5.starforceDeBleu.listener.starforceListener;

import dev.o8o1o5.starforceDeBleu.util.StarforceDataUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class SwordListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getDamager();
        ItemStack heldItem = player.getInventory().getItemInMainHand();

        if (heldItem == null || heldItem.getType().isAir() || !heldItem.getType().name().endsWith("_SWORD")) {
            return;
        }

        int stars = StarforceDataUtil.getStars(heldItem);

        if (stars <= 0 || stars > 25) {
            return;
        }

        double additionalDamage;

        if (stars <= 5) {
            additionalDamage = stars * 0.2;
        } else if (stars <= 10) {
            additionalDamage = (5 * 0.2) + ((stars - 5) * 0.3);
        } else if (stars <= 15) {
            additionalDamage = (5 * 0.2) + (5 * 0.3) + ((stars - 10) * 0.4);
        } else if (stars <= 20) {
            additionalDamage = (5 * 0.2) + (5 * 0.3) + (5 * 0.4) + ((stars - 15) * 0.5);
        } else {
            additionalDamage = (5 * 0.2) + (5 * 0.3) + (5 * 0.4) + (5 * 0.5) + ((stars - 20) * 0.6);
        }

        double addtionalDamagePercentage = 1.0;
        int currentStars = stars;

        while (currentStars > 5) {
            addtionalDamagePercentage += 0.016;
            currentStars -= 5;
        }

        event.setDamage(addtionalDamagePercentage * (event.getDamage() + additionalDamage));
    }
}
