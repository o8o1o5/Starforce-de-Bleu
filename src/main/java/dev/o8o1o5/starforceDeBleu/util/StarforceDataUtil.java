package dev.o8o1o5.starforceDeBleu.util;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

public class StarforceDataUtil {
    private static JavaPlugin plugin;

    private static NamespacedKey STARFORCE_STARS_KEY;
    private static NamespacedKey STARFORCE_PROCESSED_KEY;
    private static NamespacedKey ORIGINAL_ITEM_TYPE_KEY;

    public static void initialize(JavaPlugin p) {
        plugin = p;
        STARFORCE_STARS_KEY = new NamespacedKey(plugin, "starforce_stars");
        STARFORCE_PROCESSED_KEY = new NamespacedKey(plugin, "starforce_processed");
        ORIGINAL_ITEM_TYPE_KEY = new NamespacedKey(plugin, "original_item_type");
    }

    public static void setStars(ItemStack item, int stars) {
        if (item == null || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(STARFORCE_STARS_KEY, PersistentDataType.INTEGER, stars);
        item.setItemMeta(meta);
    }

    public static int getStars(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return 0;
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().getOrDefault(STARFORCE_STARS_KEY, PersistentDataType.INTEGER, 0);
    }

    public static void setProcessed(ItemStack item, boolean processed) {
        if (item == null || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(STARFORCE_PROCESSED_KEY, PersistentDataType.BOOLEAN, processed);
        item.setItemMeta(meta);
    }

    public static boolean isProcessed(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().getOrDefault(STARFORCE_PROCESSED_KEY, PersistentDataType.BOOLEAN, false);
    }

    public static void setOriginalItemType(ItemStack item, String typeName) {
        if (item == null || !item.hasItemMeta()) return;
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(ORIGINAL_ITEM_TYPE_KEY, PersistentDataType.STRING, typeName);
        item.setItemMeta(meta);
    }

    public static String getOriginalItemType(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return null;
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().get(ORIGINAL_ITEM_TYPE_KEY, PersistentDataType.STRING);
    }
}
