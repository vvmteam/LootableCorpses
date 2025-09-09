package com.devconnor.lootablecorpses.listeners;

import com.devconnor.lootablecorpses.LootableCorpses;
import com.devconnor.lootablecorpses.managers.CorpseManager;
import org.bukkit.scheduler.BukkitRunnable;

public class AutoRemover extends BukkitRunnable {

    private final LootableCorpses lootableCorpses;
    private final CorpseManager corpseManager;

    public AutoRemover(LootableCorpses lootableCorpses, CorpseManager corpseManager) {
        this.lootableCorpses = lootableCorpses;
        this.corpseManager = corpseManager;
    }

    public void start() {
        runTaskTimer(lootableCorpses, 0, 600);
    }

    @Override
    public void run() {
        corpseManager.clear(false);
    }
}
