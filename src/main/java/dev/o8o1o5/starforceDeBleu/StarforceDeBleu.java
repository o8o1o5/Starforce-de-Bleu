package dev.o8o1o5.starforceDeBleu;

import dev.o8o1o5.starforceDeBleu.command.StarforceCommand;
import dev.o8o1o5.starforceDeBleu.listener.SwordListener;
import dev.o8o1o5.starforceDeBleu.util.StarforceDataUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class StarforceDeBleu extends JavaPlugin {

    @Override
    public void onEnable() {
        StarforceDataUtil.initialize(this);
        StarforceCommand starforceCommandHandler = new StarforceCommand(this);
        getCommand("starforce").setExecutor(starforceCommandHandler);
        getCommand("starforce").setTabCompleter(starforceCommandHandler); // 이 줄은 꼭 추가되어야 합니다.

        getServer().getPluginManager().registerEvents(new SwordListener(), this);

        Bukkit.getLogger().info("스타포스 시스템이 활성화 되었습니다.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("스타포스 시스템이 비활성화 되었습니다.");
    }
}
