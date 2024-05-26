package ru.incrementstudio.incapi.modules.playerpoints;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class Points {
    private final Plugin plugin;
    private PlayerPointsAPI playerPointsAPI;
    public final boolean isPlayerPointsSetup;

    public Points(Plugin plugin) {
        this.plugin = plugin;
        if (!setupPoints()) {
            plugin.getLogger().severe("Не удалось зарегистрировать денежную систему &6PlayerPoints");
            isPlayerPointsSetup = false;
            return;
        }
        isPlayerPointsSetup = true;
    }

    private boolean setupPoints() {
        if (plugin.getServer().getPluginManager().getPlugin("PlayerPoints") == null) {
            return false;
        }
        playerPointsAPI = PlayerPoints.getInstance().getAPI();
        return true;
    }

    public void giveMoney(OfflinePlayer player, int count) {
        playerPointsAPI.give(player.getUniqueId(), count);
    }

    public void takeMoney(OfflinePlayer player, int count) {
        playerPointsAPI.take(player.getUniqueId(), count);
    }

    public void takeMoney(OfflinePlayer player, int count, CommandSender sender, String errorMessage) {
        if (getBalance(player) < count){
            sender.sendMessage(errorMessage
                    .replace("%balance%", String.valueOf(getBalance(player)))
                    .replace("%required%", String.valueOf(count))
                    .replace("%needed%", String.valueOf(count - getBalance(player))));
            return;
        }
        takeMoney(player, count);
    }

    public int getBalance(OfflinePlayer player) {
        return playerPointsAPI.look(player.getUniqueId());
    }

    public void setMoney(OfflinePlayer player, int amount) {
        playerPointsAPI.set(player.getUniqueId(), amount);
    }

    public void resetMoney(OfflinePlayer player) {
        playerPointsAPI.reset(player.getUniqueId());
    }

    public PlayerPointsAPI getPlayerPointsEconomy() {
        return playerPointsAPI;
    }
}
