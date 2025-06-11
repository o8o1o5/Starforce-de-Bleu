package dev.o8o1o5.starforceDeBleu.manager;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GuiManager {
    public static Inventory getStarforceGUI() {
        Inventory starforceGUI = Bukkit.createInventory(null, 36, "스타포스 강화");

        ItemStack confirmItem = new ItemStack(Material.NETHER_STAR);
        ItemMeta confirmMeta = confirmItem.getItemMeta();
        confirmMeta.setDisplayName(ChatColor.YELLOW + "스타포스 강화");
        List<String> confirmLore = new ArrayList<>();
        confirmLore.add(ChatColor.WHITE + "눌러서 강화를 시도합니다.");
        confirmLore.add("\n");
        confirmLore.add(ChatColor.WHITE + "성공 확률: ");
        confirmLore.add(ChatColor.WHITE + "실패 확률: ");
        confirmLore.add(ChatColor.WHITE + "파괴 확률: ");
        confirmMeta.setLore(confirmLore);
        confirmItem.setItemMeta(confirmMeta);

        ItemStack changeItem = new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA);
        ItemMeta changeMeta = changeItem.getItemMeta();
        changeMeta.setDisplayName("스타포스 강화");
        changeItem.setItemMeta(changeMeta);

        starforceGUI.setItem(13, changeItem);
        starforceGUI.setItem(22, confirmItem);

        return starforceGUI;
    }
}
