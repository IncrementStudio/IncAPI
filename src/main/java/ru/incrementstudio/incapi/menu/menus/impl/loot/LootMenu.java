package ru.incrementstudio.incapi.menu.menus.impl.loot;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import ru.incrementstudio.incapi.IncPlugin;
import ru.incrementstudio.incapi.configs.Config;
import ru.incrementstudio.incapi.menu.Data;
import ru.incrementstudio.incapi.menu.holders.impl.LootInventoryHolder;
import ru.incrementstudio.incapi.menu.menus.Menu;
import ru.incrementstudio.incapi.utils.ColorUtil;
import ru.incrementstudio.incapi.utils.builders.ItemBuilder;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LootMenu extends Menu {
    private Config config;
    private ConfigurationSection lootSection;
    private final Map<Integer, ItemStack> items = new HashMap<>();

    private String title;
    private int size;
    private Material borderMaterial;
    private Material chanceMaterial;
    private Inventory inventory;

    public LootMenu(IncPlugin plugin, Config config, ConfigurationSection lootSection) {
        super(plugin);
        registerListener(new LootListener());
        if (config == null) {
            plugin.getLogger().error(
                    "Ошибка при получении конфига",
                    "Конфиг для сохранения лута не найден!"
            );
            return;
        }
        if (lootSection == null) {
            plugin.getLogger().error(
                    "Ошибка при получении секции",
                    "Секция, в которой нужно сохранять лут, не найдена!"
            );
            return;
        }
        this.config = config;
        this.lootSection = lootSection;
        setItemsToBase();

        this.title = "Настройка лута";
        this.size = 54;
        this.borderMaterial = Material.BLACK_STAINED_GLASS_PANE;
        this.chanceMaterial = Material.COMPASS;
    }

    public LootMenu setTitle(String title) {
        this.title = ColorUtil.toColor(title);
        return this;
    }

    public LootMenu setSize(int size) {
        if (size >= 9 && size <= 54 && size % 9 == 0) this.size = size;
        return this;
    }

    public LootMenu setBorderMaterial(Material borderMaterial) {
        this.borderMaterial = borderMaterial;
        return this;
    }

    public LootMenu setChanceMaterial(Material chanceMaterial) {
        this.chanceMaterial = chanceMaterial;
        return this;
    }

    private void setItemsToBase() {
        items.clear();
        for (String key : lootSection.getKeys(false)) {
            try {
                int slot = Integer.parseInt(key);
                ConfigurationSection itemSection = lootSection.getConfigurationSection(key);
                if (itemSection == null) {
                    getPlugin().getLogger().warn(
                            "Ошибка при получении лута",
                            "Секция с предметом по слоту &6" + key + " &cне найдена!"
                    );
                    continue;
                }
                ItemStack itemStack = itemSection.getItemStack("item");
                if (itemStack == null) {
                    getPlugin().getLogger().warn(
                            "Ошибка при получении лута",
                            "Предмет по слоту &6" + key + " &cравен null!"
                    );
                    continue;
                }
                items.put(slot, itemStack);
            } catch (NumberFormatException e) {
                getPlugin().getLogger().warn(
                        "Ошибка при получении лута",
                        "&6" + key + " &cне является номером слота!"
                );
            }
        }
    }

    private void setItemsToInventory() {
        if (inventory == null) return;
        inventory.clear();
        for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
            int slot = entry.getKey();
            ItemStack itemStack = entry.getValue();
            inventory.setItem(slot, itemStack);
        }
    }

    public LootMenu apply() {
        inventory = Bukkit.createInventory(
                new LootInventoryHolder(true, true, true, this, LootMenuType.MAIN),
                size, title);
        setItemsToInventory();
        return this;
    }

    public void save() {
        for (int i = 0; i < inventory.getSize(); i++) {
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null || itemStack.getType().isAir()) {
                if (lootSection.contains("" + i))
                    lootSection.set("" + i, null);
                continue;
            }
            ConfigurationSection itemSection = lootSection.getConfigurationSection("" + i);
            if (itemSection == null) {
                itemSection = lootSection.createSection("" + i);
                itemSection.set("item", itemStack);
                itemSection.set("chance", 100);
            } else {
                itemSection.set("item", itemStack);
            }
        }
        config.save();
        setItemsToBase();
        setItemsToInventory();
    }

    public void showEditMenu(Player player, int slot) {
        Inventory chanceIntentory = Bukkit.createInventory(
                new LootInventoryHolder(false, false, false, this, LootMenuType.CHANCE),
                27, "Редактирование предмета");
        chanceIntentory.setItem(13, getChanceItem(slot));
        int[] border =
                {0,  1,  2,  3,  4,  5,  6,  7,  8,
                 9,                              17,
                 18, 19, 20, 21, 22, 23, 24, 25, 26};
        for (int borderSlot: border) {
            chanceIntentory.setItem(borderSlot, new ItemBuilder(borderMaterial).setName("").build());
        }
        getViewers().put(player, new Data());
        player.openInventory(chanceIntentory);
    }

    private ItemStack getChanceItem(int slot) {
        int chance = lootSection.getInt(slot + ".chance", 100);
        return new ItemBuilder(chanceMaterial)
                .setName("&bШанс появления")
                .setLore(Arrays.asList(
                        "&fШанс предмета: &a" + chance + "%",
                        "&fНажмите &eЛКМ&f, чтобы увеличить на &e1",
                        "&fНажмите &eПКМ&f, чтобы уменьшить на &e1",
                        "&fНажмите &eShift + ЛКМ&f, чтобы увеличить на &e10",
                        "&fНажмите &eShift + ПКМ&f, чтобы уменьшить на &e10"))
                .setPersistentData(new ItemBuilder.PersistentData("slot", PersistentDataType.INTEGER, slot))
                .build();
    }


    @Override
    public void show(Player player) {
        getViewers().put(player, new Data());
        player.openInventory(inventory);
    }

    public void show(Player player, Data data) {
        getViewers().put(player, data);
        player.openInventory(inventory);
    }

    public Map<Integer, ItemStack> getItems() {
        return items;
    }

    public ConfigurationSection getLootSection() {
        return lootSection;
    }

    public Config getConfig() {
        return config;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Material getChanceMaterial() {
        return chanceMaterial;
    }

    public Material getBorderMaterial() {
        return borderMaterial;
    }

    public int getSize() {
        return size;
    }

    public String getTitle() {
        return title;
    }
}
