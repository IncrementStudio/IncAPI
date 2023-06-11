package ru.incrementstudio.incrementstudioapi.modules.vault;

import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import ru.incrementstudio.incrementstudioapi.Logger;

public class Economy {
    private Plugin plugin;
    private net.milkbowl.vault.economy.Economy economy;
    public final boolean isVaultSetup;
    public Economy(Plugin plugin) {
        this.plugin = plugin;
        if (!setupEconomy()) {
            new Logger(plugin).error("Не удалось зарегистрировать денежную систему Vault");
            isVaultSetup = false;
            return;
        }
        isVaultSetup = true;
    }

    private boolean setupEconomy() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<net.milkbowl.vault.economy.Economy> rsp = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public void giveMoney(OfflinePlayer player, double count) {
        economy.depositPlayer(player, count);
    }

    public void takeMoney(OfflinePlayer player, double count) {
       economy.withdrawPlayer(player, count);
    }

    public void takeMoney(OfflinePlayer player, double count, CommandSender sender, String errorMessage) {
        if (getBalance(player) < count){
            sender.sendMessage(errorMessage
                    .replace("%balance%", String.valueOf(getBalance(player)))
                    .replace("%required%", String.valueOf(count))
                    .replace("%needed%", String.valueOf(count - getBalance(player))));
            return;
        }
        economy.withdrawPlayer(player, count);
    }

    public double getBalance(OfflinePlayer player) {
        return economy.getBalance(player);
    }

    public String format(double money) {
        return economy.format(money);
    }

    public net.milkbowl.vault.economy.Economy getVaultEconomy() {
        return economy;
    }
}
