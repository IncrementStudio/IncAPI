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
        return ItemUtil.createItemStack(
                Material.BLACK_STAINED_GLASS_PANE,
                1,
                " ",
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                new HashMap<>() {{
                    put(NamespacedKey.fromString("id"), "INC_ELEMENT");
                    put(NamespacedKey.fromString("tag"), "BORDER");
                }}
        );
    }

    public static ItemStack getIncrementStudioLink() {
        return ItemUtil.createItemStack(
                Material.REPEATING_COMMAND_BLOCK,
                1,
                ColorUtil.toColor("&5IncrementStudio++"),
                new ArrayList<>(),
                new ArrayList<>(),
                List.of(ItemFlag.HIDE_ENCHANTS),
                new HashMap<>() {{
                    put(NamespacedKey.fromString("tag"), "INC_ELEMENT");
                    put(NamespacedKey.fromString("action"), "LINK");
                    put(NamespacedKey.fromString("link"), "https://vk.com/incrementstudio");
                }}
        );
    }
}
