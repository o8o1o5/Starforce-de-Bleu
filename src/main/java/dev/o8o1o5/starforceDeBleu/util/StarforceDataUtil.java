package dev.o8o1o5.starforceDeBleu.util;

import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class StarforceDataUtil {
    private static JavaPlugin plugin;

    private static NamespacedKey STARFORCE_STARS_KEY;
    private static NamespacedKey STARFORCE_PROCESSED_KEY;
    private static NamespacedKey STARFORCE_STARFORCABLE_KEY;

    public static int MAX_STARFORCE_LEVEL = 25;

    private static final Set<Material> DEFAULT_STARFORCABLE_MATERIALS = new HashSet<>(Arrays.asList(
            // 무기
            Material.WOODEN_SWORD,   Material.STONE_SWORD,   Material.IRON_SWORD,   Material.GOLDEN_SWORD,   Material.DIAMOND_SWORD,   Material.NETHERITE_SWORD,
            Material.WOODEN_AXE,     Material.STONE_AXE,     Material.IRON_AXE,     Material.GOLDEN_AXE,     Material.DIAMOND_AXE,     Material.NETHERITE_AXE,
            Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE, Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE,
            Material.WOODEN_SHOVEL,  Material.STONE_SHOVEL,  Material.IRON_SHOVEL,  Material.GOLDEN_SHOVEL,  Material.DIAMOND_SHOVEL,  Material.NETHERITE_SHOVEL,
            Material.WOODEN_HOE,     Material.STONE_HOE,     Material.IRON_HOE,     Material.GOLDEN_HOE,     Material.DIAMOND_HOE,     Material.NETHERITE_HOE,
            Material.BOW, Material.CROSSBOW, Material.TRIDENT,
            // Material.SHEARS, Material.FLINT_AND_STEEL, Material.FISHING_ROD,
            // 방어구
            Material.LEATHER_HELMET,   Material.LEATHER_CHESTPLATE,   Material.LEATHER_LEGGINGS,   Material.LEATHER_BOOTS,
            Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS,
            Material.IRON_HELMET,      Material.IRON_CHESTPLATE,      Material.IRON_LEGGINGS,      Material.IRON_BOOTS,
            Material.GOLDEN_HELMET,    Material.GOLDEN_CHESTPLATE,    Material.GOLDEN_LEGGINGS,    Material.GOLDEN_BOOTS,
            Material.DIAMOND_HELMET,   Material.DIAMOND_CHESTPLATE,   Material.DIAMOND_LEGGINGS,   Material.DIAMOND_BOOTS,
            Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS,
            Material.TURTLE_HELMET, Material.ELYTRA, Material.SHIELD
            // 필요에 따라 더 많은 Material을 추가할 수 있습니다.
    ));

    public static void initialize(JavaPlugin p) {
        plugin = p;
        if (plugin == null) {
            return;
        }

        STARFORCE_STARS_KEY = new NamespacedKey(plugin, "starforce_stars");
        STARFORCE_PROCESSED_KEY = new NamespacedKey(plugin, "starforce_processed");
        STARFORCE_STARFORCABLE_KEY = new NamespacedKey(plugin, "starforce_starforcable");
    }

    public static void setStars(ItemStack item, int stars) {
        if (item == null) {
            return;
        }

        ItemMeta meta;
        if (!item.hasItemMeta()) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
            if (meta == null) {
                return;
            }
            item.setItemMeta(meta);
        } else {
            meta = item.getItemMeta();
        }

        meta.getPersistentDataContainer().set(STARFORCE_STARS_KEY, PersistentDataType.INTEGER, stars);
        item.setItemMeta(meta);

        ItemLoreDisplayUtil.updateItemLore(item, stars);
    }

    public static int getStars(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return 0;
        }
        ItemMeta meta = item.getItemMeta();
        int retrievedStars = meta.getPersistentDataContainer().getOrDefault(STARFORCE_STARS_KEY, PersistentDataType.INTEGER, 0);
        return retrievedStars;
    }

    public static void setProcessed(ItemStack item, boolean processed) {
        if (item == null || !item.hasItemMeta()) {
            return;
        }
        ItemMeta meta = item.getItemMeta();
        meta.getPersistentDataContainer().set(STARFORCE_PROCESSED_KEY, PersistentDataType.BOOLEAN, processed);
        item.setItemMeta(meta);
    }

    public static boolean isProcessed(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        boolean processed = meta.getPersistentDataContainer().getOrDefault(STARFORCE_PROCESSED_KEY, PersistentDataType.BOOLEAN, false);
        return processed;
    }

    public static void setStarforcable(ItemStack item, boolean starforcable) {
        if (item == null) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
            if (meta == null) {
                return;
            }
        }

        meta.getPersistentDataContainer().set(STARFORCE_STARFORCABLE_KEY, PersistentDataType.BOOLEAN, starforcable);
        item.setItemMeta(meta);
    }

    public static boolean isStarforcable(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }
        ItemMeta meta = item.getItemMeta();
        return meta.getPersistentDataContainer().getOrDefault(STARFORCE_STARFORCABLE_KEY, PersistentDataType.BOOLEAN, false);
    }

    public static boolean hasRelevantAttributeModifiers(ItemStack item) {
        if (item == null) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            meta = Bukkit.getItemFactory().getItemMeta(item.getType());
            if (meta == null) {
                return false;
            }
        }

        if (meta.hasAttributeModifiers()) {
            Multimap<Attribute, AttributeModifier> modifiers = meta.getAttributeModifiers();
            if (!modifiers.isEmpty()) {
                return true;
            }
        }

        if (DEFAULT_STARFORCABLE_MATERIALS.contains(item.getType())) {
            return true;
        }

        return false;
    }
}
