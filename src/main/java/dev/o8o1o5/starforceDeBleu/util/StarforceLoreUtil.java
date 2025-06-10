package dev.o8o1o5.starforceDeBleu.util;

import com.google.common.collect.Multimap; // 사용하지 않으므로 제거 가능
import org.bukkit.ChatColor;
import org.bukkit.Material; // isSword 메서드를 위해 필요
import org.bukkit.attribute.Attribute; // 사용하지 않으므로 제거 가능
import org.bukkit.attribute.AttributeModifier; // 사용하지 않으므로 제거 가능
import org.bukkit.inventory.EquipmentSlot; // 사용하지 않으므로 제거 가능
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat; // 사용하지 않으므로 제거 가능
import java.util.List;
import java.util.ArrayList;

public class StarforceLoreUtil {
    private static final String STARFORCE_LORE_PREFIX_TEXT = "스타포스";
    // 우리가 추가할 스타포스 로어의 시작 패턴만 명확히 정의 (제거 로직에서 사용)
    private static final String STARFORCE_LORE_DISPLAY_PATTERN_START = ChatColor.GRAY + STARFORCE_LORE_PREFIX_TEXT + " ";


    private static final String FILLED_STAR_CHAR = "★";
    private static final int STARS_PER_GROUP = 5;

    private static final ChatColor[] STAR_LEVEL_COLORS = {
            ChatColor.WHITE, ChatColor.YELLOW, ChatColor.GOLD, ChatColor.GREEN, ChatColor.AQUA
    };
    private static final ChatColor BASE_EMPTY_STAR_COLOR = ChatColor.DARK_GRAY;

    public static void updateStarforceLore(ItemStack item, int stars) {
        if (item == null || item.getType().isAir()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return;
        }

        // 먼저 기존의 우리의 스타포스 로어를 제거합니다.
        // 이는 스타포스 레벨이 0이 되거나, 유효하지 않은 값일 때도 적용됩니다.
        removeExistingStarforceLoreInternal(meta);

        // 제거 후 meta에서 최신 lore를 다시 가져옵니다.
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = new ArrayList<>();
        }

        // 스타포스 레벨이 유효하고 0보다 클 경우에만 별 로어를 추가합니다.
        if (stars > 0 && stars <= StarforceDataUtil.MAX_STARFORCE_LEVEL) {
            StringBuilder starLoreBuilder = new StringBuilder();
            starLoreBuilder.append(ChatColor.GRAY).append(STARFORCE_LORE_PREFIX_TEXT).append(" ");

            int currentSegmentIndex = (stars - 1) / STARS_PER_GROUP;
            ChatColor currentFilledColor = (currentSegmentIndex >= 0 && currentSegmentIndex < STAR_LEVEL_COLORS.length)
                    ? STAR_LEVEL_COLORS[currentSegmentIndex]
                    : BASE_EMPTY_STAR_COLOR;
            ChatColor previousFilledColor = (currentSegmentIndex > 0 && currentSegmentIndex - 1 < STAR_LEVEL_COLORS.length)
                    ? STAR_LEVEL_COLORS[currentSegmentIndex - 1]
                    : BASE_EMPTY_STAR_COLOR;
            int currentGroupFilledStars = stars % STARS_PER_GROUP;
            if (currentGroupFilledStars == 0 && stars > 0) { // 5성, 10성 등 딱 떨어지는 경우
                currentGroupFilledStars = STARS_PER_GROUP;
            }

            for (int i = 0; i < currentGroupFilledStars; i++) {
                starLoreBuilder.append(currentFilledColor).append(FILLED_STAR_CHAR);
            }
            for (int i = 0; i < (STARS_PER_GROUP - currentGroupFilledStars); i++) {
                starLoreBuilder.append(previousFilledColor).append(FILLED_STAR_CHAR);
            }

            // 기존 로어 리스트의 가장 위에 별 로어를 추가합니다.
            lore.add(0, starLoreBuilder.toString());
        }

        // 메타에 최종 로어 리스트를 설정하고 아이템에 적용합니다.
        meta.setLore(lore);
        item.setItemMeta(meta);
    }

    /**
     * 아이템의 ItemMeta에서 우리가 추가했던 스타포스 관련 로어 (별 개수 표시 등)만 제거합니다.
     * 마인크래프트 기본 Attribute 로어나 다른 커스텀 로어는 건드리지 않습니다.
     *
     * @param meta 로어를 정리할 ItemMeta.
     */
    private static void removeExistingStarforceLoreInternal(ItemMeta meta) {
        List<String> lore = meta.getLore();

        if (lore == null || lore.isEmpty()) {
            return;
        }

        List<String> cleanedLore = new ArrayList<>();
        boolean removedAnyLine = false;

        // 여기서는 우리가 추가했던 "별 개수" 로어만 제거하도록 로직을 수정합니다.
        // 이전에 포함되었던 "주로 사용하는 손에 있을 때:", "공격 피해", "공격 속도" 관련 로어 제거 로직은 삭제합니다.
        for (String line : lore) {
            String strippedLine = ChatColor.stripColor(line);

            // 우리가 추가한 별 로어는 "스타포스 ★" 패턴으로 시작합니다.
            // 이전에 "STARFORCE_LORE_START_PATTERN"으로 정의했던 것과 동일한 의미입니다.
            if (strippedLine.startsWith(ChatColor.stripColor(STARFORCE_LORE_DISPLAY_PATTERN_START))) {
                removedAnyLine = true;
                continue; // 이 줄 제거
            }

            // ⭐ 중요: 아래의 Attribute 관련 로어 제거 로직은 완전히 삭제합니다. ⭐
            // 마인크래프트 기본 Attribute 로어를 건드리지 않기 위함입니다.
            /*
            if (line.contains("주로 사용하는 손에 있을 때:") ||
                strippedLine.contains("공격 피해") ||
                strippedLine.contains("공격 속도")) {
                removedAnyLine = true;
                continue;
            }
            */
            // ⭐ 이 부분의 주석을 해제하면 안 됩니다. ⭐

            // 위에 해당하지 않는 (즉, 바닐라 로어 또는 다른 사용자 정의 로어)는 유지
            cleanedLore.add(line);
        }

        if (removedAnyLine) { // 실제로 로어 라인이 제거되었다면 업데이트
            meta.setLore(cleanedLore);
        }
    }

    // isSword 메서드는 이 클래스에서 직접 사용되지 않으므로 제거할 수 있습니다.
    // 하지만 StarforceAttributeUtil.isSword(item.getType()) 호출을 위해 ItemAttributeUtil이 필요하다는 점을 명심하십시오.
    // (현재는 StarforceAttributeUtil에 isSword가 있으므로 여기서는 필요 없음)
    /*
    private static boolean isSword(Material material) {
        return material.name().endsWith("_SWORD");
    }
    */
}