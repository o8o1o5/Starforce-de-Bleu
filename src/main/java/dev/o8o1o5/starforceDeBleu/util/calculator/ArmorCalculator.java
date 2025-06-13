package dev.o8o1o5.starforceDeBleu.util.calculator;

import org.bukkit.inventory.ItemStack;

public class ArmorCalculator {
    public static double getReducibleDamage(ItemStack item, int stars) {
        double reducibleDamage;

        reducibleDamage = stars * 0.1;
        return reducibleDamage;
    }

    public static double getReducibleDamagePercentage(ItemStack item, int stars) {
        double reducibleDamagePercentage = 0.0;
        int currentStars = stars;

        while (currentStars >= 5) {
            reducibleDamagePercentage += 0.01;
            currentStars -= 5;
        }
        return reducibleDamagePercentage;
    }
}
