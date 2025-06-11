package dev.o8o1o5.starforceDeBleu.listener.starforceListener;

import dev.o8o1o5.starforceDeBleu.util.StarforceDataUtil;
import dev.o8o1o5.starforceDeBleu.util.calculator.SwordCalculator;
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

        double additionalDamage = SwordCalculator.getAdditionalDamage(heldItem, stars);
        double additionalDamagePercentage = SwordCalculator.getAdditionalDamagePercentage(heldItem, stars);

        double calculatedDamage = additionalDamagePercentage * (event.getDamage() + additionalDamage);

        event.setDamage(calculatedDamage);
    }
}
