package ru.incrementstudio.incapi.utils;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MenuUtil {
    public static ItemStack getBorderItem() {
        return new ItemBuilder()
                .setMaterial(Material.BLACK_STAINED_GLASS_PANE)
                .setAmount(1)
                .setName(" ")
                .build();
    }

    public static ItemStack getIncrementStudioLink() {
        return new ItemBuilder()
                .setMaterial(Material.REPEATING_COMMAND_BLOCK)
                .setAmount(1)
                .setName("&5IncrementStudio++")
                .setGlowing(true)
                .build();
    }
}
