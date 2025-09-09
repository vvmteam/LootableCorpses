package com.devconnor.lootablecorpses.managers;

import com.comphenix.protocol.events.PacketContainer;
import com.devconnor.lootablecorpses.LootableCorpses;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.bukkit.entity.Player;

import java.util.List;

@UtilityClass
@Slf4j
public class PacketManager {

    private static LootableCorpses lootableCorpses;

    public static void loadPacketManager(LootableCorpses lootableCorpses) {
        PacketManager.lootableCorpses = lootableCorpses;
    }

    public static void sendPacketsToServer(List<PacketContainer> packets) {
        for (Player player : lootableCorpses.getPlayers()) {
            for (PacketContainer packet : packets) {
                sendPacketToPlayer(player, packet);
            }
        }
    }

    public static void sendPacketsToPlayer(Player player, List<PacketContainer> packets) {
        for (PacketContainer packet : packets) {
            sendPacketToPlayer(player, packet);
        }
    }

    public static void sendPacketToPlayer(Player player, PacketContainer packet) {
        try {
            lootableCorpses.getProtocolManager().sendServerPacket(player, packet);
        } catch (Exception e) {
            log.error("Failed to send packet to player {}", player.getName(), e);
        }
    }
}
