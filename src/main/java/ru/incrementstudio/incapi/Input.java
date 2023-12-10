package ru.incrementstudio.incapi;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class Input implements Listener {
    private static final HashMap<Player, Map.Entry<Consumer<String>, Map.Entry<Boolean, Action>>> players = new HashMap<>();
    public static void addListener(Player player, Consumer<String> onChat) {
        players.put(player, Map.entry(onChat, Map.entry(false, () -> {})));
    }
    public static void addCancellableListener(Player player, Consumer<String> onChat, Action onCancel) {
        players.put(player, Map.entry(onChat, Map.entry(true, onCancel)));
    }
    public static void removeListener(Player player) {
        if (!players.containsKey(player)) return;
        players.remove(player);
    }

    @EventHandler
    public void onChat(PlayerChatEvent event) {
        if (!players.containsKey(event.getPlayer())) return;

        players.get(event.getPlayer()).getKey().accept(event.getMessage());
        event.setCancelled(true);
    }

    @EventHandler
    public void onShift(PlayerToggleSneakEvent event) {
        if (!players.containsKey(event.getPlayer())) return;
        if (!players.get(event.getPlayer()).getValue().getKey()) return;

        players.get(event.getPlayer()).getValue().getValue().execute();
        players.remove(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        players.remove(event.getPlayer());
    }
}
