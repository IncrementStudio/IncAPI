package ru.incrementstudio.incapi;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.function.Consumer;

public class Input implements Listener {
    public static HashMap<Player, Consumer<? super String>> players = new HashMap<>();

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        if (players.containsKey(event.getPlayer())) {
            event.setCancelled(true);
            players.get(event.getPlayer()).accept(event.getMessage());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        players.remove(event.getPlayer());
    }
}
