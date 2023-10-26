package ru.incrementstudio.incapi.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.persistence.PersistentDataType;
import ru.incrementstudio.incapi.configs.templates.EnchantmentTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemUtil {
    public static ItemStack getHead(OfflinePlayer player) {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skull = (SkullMeta) item.getItemMeta();
        if (player.getPlayer() != null) {
            skull.setPlayerProfile(player.getPlayer().getPlayerProfile());
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
