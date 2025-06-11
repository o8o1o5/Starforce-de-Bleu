package dev.o8o1o5.starforceDeBleu.util;

import dev.o8o1o5.starforceDeBleu.util.calculator.SwordCalculator;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class StarforceAttributeLoreUtil {
    public static final String ATTRIBUTE_DAMAGE_LORE_SURFIX = ChatColor.GOLD + "추가 피해";
    public static final String ATTRIBUTE_DAMAGE_PERCENT_LORE_SURFIX = ChatColor.GOLD + "추가 피해 배율";

    public static void addAttributeLore(ItemStack item, int stars) {
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        List<String> lore = meta.getLore();
        if (lore == null) return;

        if (stars <= 0 || stars > StarforceDataUtil.MAX_STARFORCE_LEVEL) {
            meta.setLore(lore);
            return;
        }

        Material type = item.getType();

        lore.add("");
        lore.add(ChatColor.GRAY + "스타포스 효과:");

        if (type.name().endsWith("_SWORD")) {
            double additionalDamage = SwordCalculator.getAdditionalDamage(item, stars);
            double additionalDamagePercentage = SwordCalculator.getAdditionalDamagePercentage(item, stars);

            String formattedAddDamage = String.format("%.1f", additionalDamage);
            String formattedAddPercentage = String.format("%.1f", (additionalDamagePercentage - 1.0) * 100);

            if (additionalDamage > 0) {
                lore.add(" " + ChatColor.GOLD + formattedAddDamage + " " + ATTRIBUTE_DAMAGE_LORE_SURFIX);
            }

            if (additionalDamagePercentage > 1.0) {
                lore.add(" " + ChatColor.GOLD + formattedAddPercentage + "%" + " " + ATTRIBUTE_DAMAGE_PERCENT_LORE_SURFIX);
            }
        }

        meta.setLore(lore);
        item.setItemMeta(meta);
    }
}
