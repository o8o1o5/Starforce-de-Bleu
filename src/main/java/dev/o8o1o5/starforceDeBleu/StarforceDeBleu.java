package dev.o8o1o5.starforceDeBleu;

import dev.o8o1o5.starforceDeBleu.command.StarforceCommand;
import dev.o8o1o5.starforceDeBleu.listener.AnvilInteractListener;
import dev.o8o1o5.starforceDeBleu.listener.StarforceGUIListener;
import dev.o8o1o5.starforceDeBleu.listener.starforceListener.ArmorListener;
import dev.o8o1o5.starforceDeBleu.listener.starforceListener.SwordListener;
import dev.o8o1o5.starforceDeBleu.manager.StarforceManager;
import dev.o8o1o5.starforceDeBleu.util.StarforceDataUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class StarforceDeBleu extends JavaPlugin {
    private StarforceManager starforceManager;

    @Override
    public void onEnable() {
        StarforceDataUtil.initialize(this);
        this.starforceManager = new StarforceManager();

        StarforceCommand starforceCommandHandler = new StarforceCommand(this);
        getCommand("starforce").setExecutor(starforceCommandHandler);
        getCommand("starforce").setTabCompleter(starforceCommandHandler);

        getServer().getPluginManager().registerEvents(new SwordListener(), this);
        getServer().getPluginManager().registerEvents(new ArmorListener(), this);

        getServer().getPluginManager().registerEvents(new AnvilInteractListener(), this);
        getServer().getPluginManager().registerEvents(new StarforceGUIListener(starforceManager, this), this);

        Bukkit.getLogger().info("스타포스 시스템이 활성화 되었습니다.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("스타포스 시스템이 비활성화 되었습니다.");
    }
}
