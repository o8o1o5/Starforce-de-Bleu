package dev.o8o1o5.starforceDeBleu.manager;

import dev.o8o1o5.starforceDeBleu.data.StarforceLevel;
import dev.o8o1o5.starforceDeBleu.util.StarforceDataUtil;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Random;

public class StarforceManager {
    private final Random random;

    public StarforceManager() {
        this.random = new Random();
    }

    public ItemStack processStarforce(Player player, ItemStack item) {
        if (item == null || item.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "강화할 아이템이 없습니다.");
            return null;
        }

        int currentStars = StarforceDataUtil.getStars(item);
        StarforceLevel currentLevel = StarforceLevel.getLevel(currentStars);

        // long cost = calculateCost(currentStars);
        player.sendMessage("경제 플러그인이 연동되지 않아 재화가 차감되지 않습니다.");

        int roll = random.nextInt(100) + 1;

        ItemStack resultItem = item.clone();

        if (roll <= currentLevel.getSuccessRate()) {
            StarforceDataUtil.setStars(resultItem, StarforceDataUtil.getStars(item) + 1);
            player.sendMessage(ChatColor.GREEN + "강화 성공! 아이템의 스타포스가 " + StarforceDataUtil.getStars(resultItem) + "성이 되었습니다.");
        } else if (roll <= currentLevel.getSuccessRate() + currentLevel.getFailRate()) {
            if (currentStars <= 10) {
                player.sendMessage(ChatColor.RED + "강화 실패! 아이템의 스타포스가 유지됩니다.");
            } else {
                int newStars = Math.max(0, StarforceDataUtil.getStars(item) - 1);
                StarforceDataUtil.setStars(resultItem, newStars);
                player.sendMessage(ChatColor.RED + "강화 실패! 아이템의 스타포스가 " + StarforceDataUtil.getStars(resultItem) + "성으로 하락했습니다.");
            }
        } else {
            player.sendMessage(ChatColor.DARK_RED + "강화 파괴! 아이템이 소멸했습니다.");
            resultItem = null;
        }

        return resultItem;

    }

    private long calcualteCost(int stars) {
        return (long) (100 * Math.pow(stars + 1, 2));
    }
}
