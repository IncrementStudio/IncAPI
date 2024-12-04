package ru.incrementstudio.incapi.util;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class ItemUtil {
    public static ItemStack getHead(OfflinePlayer player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        if (player != null && player.getPlayer() != null) {
            skull.setOwningPlayer(player.getPlayer());
        }
        item.setItemMeta(skull);
        return item;
    }

    public static int calcItemStackInPlayerInventory(Player player, ItemStack itemStack) {
        int count = 0;
        for (int i = 0; i < player.getInventory().getSize(); ++i) {
            ItemStack stack;
            if (i == 40 || i == 38 || i == 37 || i == 36 || i == 39 || (stack = player.getInventory().getItem(i)) == null || !stack.isSimilar(itemStack)) continue;
            count += stack.getAmount();
        }
        return count;
    }
}
