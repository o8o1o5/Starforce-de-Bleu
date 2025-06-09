package dev.o8o1o5.starforceDeBleu.command;

import dev.o8o1o5.starforceDeBleu.StarforceDeBleu;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class StarfoceCommand implements CommandExecutor {
    private final StarforceDeBleu plugin;

    public StarfoceCommand(StarforceDeBleu plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String @NotNull [] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "이 명령어는 플레이어만 사용할 수 있습니다.");
            return true;
        }

        player = (Player) sender;
        ItemStack handItem = player.getInventory().getItemInMainHand();

        if (handItem == null || handItem.getType().isAir()) {
            player.sendMessage(ChatColor.RED + "강화할 아이템을 손에 들어야 합니다.");
            return true;
        }

        String ARGS_MESSAGE = "====================\n"
                + "<< /starforce 사용법 >>\n"
                + "";
        if (args.length == 0) {
            player.sendMessage(ChatColor.YELLOW + );
        }
    }
}
