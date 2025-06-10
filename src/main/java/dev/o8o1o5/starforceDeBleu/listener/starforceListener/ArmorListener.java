package dev.o8o1o5.starforceDeBleu.listener.starforceListener;

import dev.o8o1o5.starforceDeBleu.util.StarforceDataUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

public class ArmorListener implements Listener {
    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getEntity();
        ItemStack helmetItem = player.getInventory().getHelmet();
        ItemStack chestplateItem = player.getInventory().getChestplate();
        ItemStack leggingsItem = player.getInventory().getLeggings();
        ItemStack bootsItem = player.getInventory().getBoots();

        if (helmetItem == null && chestplateItem == null && leggingsItem == null && bootsItem == null) {
            return;
        }

        int helmetStars = StarforceDataUtil.getStars(helmetItem);
        int chestplateStars = StarforceDataUtil.getStars(chestplateItem);
        int leggingsStars = StarforceDataUtil.getStars(leggingsItem);
        int bootsStars = StarforceDataUtil.getStars(bootsItem);

        if (helmetStars <= 0 || helmetStars > 25 ||
        chestplateStars <= 0 || chestplateStars > 25 ||
        leggingsStars <= 0 || leggingsStars > 25 ||
        bootsStars <= 0 || bootsStars > 25) {
            return;
        }

        int starSum = helmetStars + chestplateStars + leggingsStars + bootsStars;

        double reducedDamage = event.getDamage() - starSum * 0.1;

        double reducedDamagePercentage = 1.0;
        int currentStarSum = starSum;

        while (currentStarSum > 5) {
            reducedDamagePercentage -= 0.002;
            currentStarSum -= 5;
        }

        event.setDamage(reducedDamage * reducedDamagePercentage);
    }
}
