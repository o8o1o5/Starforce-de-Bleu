package dev.o8o1o5.starforceDeBleu.listener.starforceListener;

import dev.o8o1o5.starforceDeBleu.util.StarforceDataUtil;
import dev.o8o1o5.starforceDeBleu.util.calculator.ArmorCalculator;
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

        double helmetReducibleDamage = ArmorCalculator.getReducibleDamage(helmetItem, helmetStars);
        double chestplateReducibleDamage = ArmorCalculator.getReducibleDamage(chestplateItem, chestplateStars);
        double leggingsReducibleDamage = ArmorCalculator.getReducibleDamage(leggingsItem, leggingsStars);
        double bootsReducibleDamage = ArmorCalculator.getReducibleDamage(bootsItem, bootsStars);

        double helmetReducibleDamagePercentage = ArmorCalculator.getReducibleDamagePercentage(helmetItem, helmetStars);
        double chestplateReducibleDamagePercentage = ArmorCalculator.getReducibleDamagePercentage(chestplateItem, chestplateStars);
        double leggingsReducibleDamagePercentage = ArmorCalculator.getReducibleDamagePercentage(leggingsItem, leggingsStars);
        double bootsReducibleDamagePercentage = ArmorCalculator.getReducibleDamagePercentage(bootsItem, bootsStars);

        double reducibleDamageSum = helmetReducibleDamage + chestplateReducibleDamage + leggingsReducibleDamage + bootsReducibleDamage;
        double reducibleDamagePercentageSum = 1.0 - (helmetReducibleDamagePercentage + chestplateReducibleDamagePercentage + leggingsReducibleDamagePercentage + bootsReducibleDamagePercentage);

        double calculatedDamage = reducibleDamagePercentageSum * Math.max(event.getDamage() - reducibleDamageSum, 0);

        event.setDamage(calculatedDamage);
    }
}
