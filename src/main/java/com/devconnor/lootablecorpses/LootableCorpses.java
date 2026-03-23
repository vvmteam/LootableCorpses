package com.devconnor.lootablecorpses;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.devconnor.lootablecorpses.listeners.CommandListener;
import com.devconnor.lootablecorpses.listeners.ConnectListener;
import com.devconnor.lootablecorpses.listeners.CorpseListener;
import com.devconnor.lootablecorpses.listeners.PacketListener;
import com.devconnor.lootablecorpses.managers.CorpseManager;
import com.devconnor.lootablecorpses.managers.PacketManager;
import com.devconnor.lootablecorpses.utils.ConfigManager;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public final class LootableCorpses extends JavaPlugin {

    private CorpseManager corpseManager;

    @Getter
    private ProtocolManager protocolManager;

    private PacketListener packetListener;

    @Getter
    private boolean isPluginEnabled = true;

    public boolean isLootingEnabled() {
        return !ConfigManager.isLootingDisabled();
    }

    @Getter
    private int corpseLifespanAfterInteraction;

    @Override
    public void onEnable() {
        ConfigManager.setupConfig(this);

        this.corpseManager = new CorpseManager(this);
        this.protocolManager = ProtocolLibrary.getProtocolManager();
        this.packetListener = new PacketListener(this, this.protocolManager, this.corpseManager);
        PacketManager.loadPacketManager(this);

        if (ConfigManager.isCorpseLifespanAfterInteractionSet()) {
            this.corpseLifespanAfterInteraction = ConfigManager.getCorpseLifespanAfterInteractionMillis();
        }

        Bukkit.getPluginManager().registerEvents(new CorpseListener(this, corpseManager), this);
        Bukkit.getPluginManager().registerEvents(new ConnectListener(corpseManager), this);

        this.packetListener.createUseEntityPacketListener();

        getCommand("lootablecorpses").setExecutor(new CommandListener(this, corpseManager));
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(Bukkit.getOnlinePlayers());
    }

    public void toggle(boolean enable) {
        this.isPluginEnabled = enable;
    }
}
