package ru.incrementstudio.incrementstudioapi.utils;

import org.bukkit.Material;
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
                List.of(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS),
                new HashMap<>() {{
                    put("id", "INC_ELEMENT");
                    put("tag", "BORDER");
                }},
                true,
                false,
                false
        );
    }

    public static ItemStack getIncrementStudioLink() {
        return ItemUtil.createItemStack(
                Material.REPEATING_COMMAND_BLOCK,
                1,
                "&5IncrementStudio++",
                new ArrayList<>(),
                List.of(ItemFlag.HIDE_ENCHANTS, ItemFlag.HIDE_POTION_EFFECTS),
                new HashMap<>() {{
                    put("tag", "INC_ELEMENT");
                    put("action", "LINK");
                    put("link", "https://vk.com/incrementstudio");
                }},
                true,
                true,
                true
        );
    }


}
