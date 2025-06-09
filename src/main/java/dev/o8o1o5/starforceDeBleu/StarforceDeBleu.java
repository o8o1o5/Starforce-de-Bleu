package dev.o8o1o5.starforceDeBleu;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class StarforceDeBleu extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("스타포스 시스템이 활성화 되었습니다.");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Bukkit.getLogger().info("스타포스 시스템이 비활성화 되었습니다.");
    }
}
