package com.devconnor.lootablecorpses.listeners;

import com.devconnor.lootablecorpses.LootableCorpses;
import com.devconnor.lootablecorpses.instances.CorpseRemoveWand;
import com.devconnor.lootablecorpses.managers.CorpseManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static com.devconnor.lootablecorpses.utils.MessageUtils.sendGreenMessage;
import static com.devconnor.lootablecorpses.utils.MessageUtils.sendRedMessage;

public class CommandListener implements CommandExecutor {

    private final LootableCorpses lootableCorpses;
    private final CorpseManager corpseManager;
    private final CorpseRemoveWand wand;

    public CommandListener(LootableCorpses lootableCorpses, CorpseManager corpseManager) {
        this.lootableCorpses = lootableCorpses;
        this.corpseManager = corpseManager;
        this.wand = new CorpseRemoveWand();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player player) {
            if (!doesPlayerHavePermissions(player)) {
                return true;
            }

            if (args.length == 1 && args[0].equalsIgnoreCase("remove")) {
                wand.givePlayerWand(player);
                sendGreenMessage(player, "Use the wand to remove corpses");
                return true;
            }
        }

        if (args.length == 1 && args[0].equalsIgnoreCase("clear")) {
            corpseManager.clear(true);
            sendGreenMessage(sender, "All corpses cleared!");
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("enable")) {
            lootableCorpses.toggle(true);
            sendGreenMessage(sender, "LootableCorpses enabled!");
            return true;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("disable")) {
            lootableCorpses.toggle(false);
            sendRedMessage(sender, "LootableCorpses disabled");
            return true;
        }

        return false;
    }

    private boolean doesPlayerHavePermissions(Player player) {
        if (player.isOp() || player.hasPermission("lootablecorpses.admin")) {
            return true;
        }

        sendRedMessage(player, "You do not have permission to use this command!");
        return false;
    }
}
