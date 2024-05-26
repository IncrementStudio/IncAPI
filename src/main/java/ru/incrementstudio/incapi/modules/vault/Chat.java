package ru.incrementstudio.incapi.modules.vault;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;

public class Chat {
    private Plugin plugin;
    private net.milkbowl.vault.chat.Chat chat;
    public final boolean isVaultSetup;
    public Chat(Plugin plugin) {
        this.plugin = plugin;
        if (!setupChat()) {
            plugin.getLogger().severe("Не удалось зарегистрировать систему чата Vault");
            isVaultSetup = false;
            return;
        }
        isVaultSetup = true;
    }

    private boolean setupChat() {
        if (plugin.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<net.milkbowl.vault.chat.Chat> rsp = plugin.getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
        if (rsp == null) {
            return false;
        }
        chat = rsp.getProvider();
        return chat != null;
    }


    public net.milkbowl.vault.chat.Chat getVaultChat() {
        return chat;
    }
}
