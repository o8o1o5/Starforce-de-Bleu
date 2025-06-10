package dev.o8o1o5.starforceDeBleu.manager;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class GuiManager {
    public static Inventory getStarforceGUI() {
        Inventory starforceGUI = Bukkit.createInventory(null, 27, "스타포스 강화");


        return starforceGUI;
    }
}
