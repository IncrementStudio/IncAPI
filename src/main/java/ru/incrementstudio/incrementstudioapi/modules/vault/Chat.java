package ru.incrementstudio.incrementstudioapi.modules.vault;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import ru.incrementstudio.incrementstudioapi.Logger;

public class Chat {
    private net.milkbowl.vault.chat.Chat chat;
    public final boolean isVaultSetup;
    public Chat(Plugin plugin) {
        if (!setupChat(plugin)) {
            new Logger(plugin).error("Не удалось зарегистрировать систему чата Vault");
            isVaultSetup = false;
            return;
        }
        isVaultSetup = true;
    }

    private boolean setupChat(Plugin plugin) {
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
