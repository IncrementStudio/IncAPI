package ru.incrementstudio.incapi.menu.menus.impl.loot;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.incrementstudio.incapi.IncPlugin;
import ru.incrementstudio.incapi.configs.Config;
import ru.incrementstudio.incapi.menu.Data;
import ru.incrementstudio.incapi.menu.holders.impl.LootInventoryHolder;
import ru.incrementstudio.incapi.menu.menus.Menu;
import ru.incrementstudio.incapi.utils.ColorUtil;

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

    public LootMenu(Config config, ConfigurationSection lootSection) {
        if (config == null) {
            IncPlugin.getInstance().getLogger().error(
                    "Ошибка при получении конфига",
                    "Конфиг для сохранения лута не найден!"
            );
            return;
        }
        if (lootSection == null) {
            IncPlugin.getInstance().getLogger().error(
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
        for (String key : lootSection.getKeys(false)) {
            try {
                int slot = Integer.parseInt(key);
                ConfigurationSection itemSection = lootSection.getConfigurationSection(key);
                if (itemSection == null) {
                    IncPlugin.getInstance().getLogger().warn(
                            "Ошибка при получении лута",
                            "Секция с предметом по слоту &6" + key + " &cне найдена!"
                    );
                    continue;
                }
                ItemStack itemStack = itemSection.getItemStack("item");
                if (itemStack == null) {
                    IncPlugin.getInstance().getLogger().warn(
                            "Ошибка при получении лута",
                            "Предмет по слоту &6" + key + " &cравен null!"
                    );
                    continue;
                }
                items.put(slot, itemStack);
            } catch (NumberFormatException e) {
                IncPlugin.getInstance().getLogger().warn(
                        "Ошибка при получении лута",
                        "&6" + key + " &cне является номером слота!"
                );
            }
        }
    }

    private void setItemsToInventory() {
        if (inventory == null) return;
        for (Map.Entry<Integer, ItemStack> entry : items.entrySet()) {
            int slot = entry.getKey();
            ItemStack itemStack = entry.getValue();
            inventory.setItem(slot, itemStack);
        }
    }

    public LootMenu apply() {
        inventory = Bukkit.createInventory(new LootInventoryHolder(true, true, true, this), size, title);
        setItemsToInventory();
        return this;
    }

    private void reopenAll() {
        for (Player player : getViewers().keySet())
            player.openInventory(inventory);
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
        reopenAll();
    }

    public void showEditMenu(Player player, int slot) {
        // TODO
//        Menu menu = new Menu() {
//            @Override
//            public void onPlayerClose(Player player, InventoryCloseEvent event) {
//                Bukkit.getScheduler().runTask(Plugin.getInstance(), () -> back.show(player));
//            }
//        };
//        menu.addPage(new Page("Редактирование предмета", 27)
//                .setSlot(new Button(getChanceItem(slot)) {
//                    @Override
//                    public void onClick(Player player, InventoryClickEvent inventoryClickEvent) {
//                        inventoryClickEvent.setCancelled(true);
//                        if (inventoryClickEvent.isLeftClick()) {
//                            if (inventoryClickEvent.isShiftClick()) {
//                                int chance = Math.min(100, Math.max(0, Plugin.config.get().getInt("phases.loot.loot." + slot + ".chance", 100) + 10));
//                                Plugin.config.get().set("phases.loot.loot." + slot + ".chance", chance);
//                            } else {
//                                int chance = Math.min(100, Math.max(0, Plugin.config.get().getInt("phases.loot.loot." + slot + ".chance", 100) + 1));
//                                Plugin.config.get().set("phases.loot.loot." + slot + ".chance", chance);
//                            }
//                        } else if (inventoryClickEvent.isRightClick()) {
//                            if (inventoryClickEvent.isShiftClick()) {
//                                int chance = Math.min(100, Math.max(0, Plugin.config.get().getInt("phases.loot.loot." + slot + ".chance", 100) - 10));
//                                Plugin.config.get().set("phases.loot.loot." + slot + ".chance", chance);
//                            } else {
//                                int chance = Math.min(100, Math.max(0, Plugin.config.get().getInt("phases.loot.loot." + slot + ".chance", 100) - 1));
//                                Plugin.config.get().set("phases.loot.loot." + slot + ".chance", chance);
//                            }
//                        }
//                        Plugin.config.save();
//                        ItemMeta meta = getItemStack().getItemMeta();
//                        meta.setLore(ColorUtil.toColor(Arrays.asList(
//                                "&fШанс предмета: &a" + Plugin.config.get().getInt("phases.loot.loot." + slot + ".chance", 100) + "%",
//                                "&fНажмите &eЛКМ&f, чтобы увеличить на &e1",
//                                "&fНажмите &eПКМ&f, чтобы уменьшить на &e1",
//                                "&fНажмите &eShift + ЛКМ&f, чтобы увеличить на &e10",
//                                "&fНажмите &eShift + ПКМ&f, чтобы уменьшить на &e10"))
//                        );
//                        getItemStack().setItemMeta(meta);
//                        inventoryClickEvent.getClickedInventory().setItem(13, getItemStack());
//                    }
//                }, 13)
//                .setSlots(new Item(new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setName(" ").build()),
//                        0, 1, 2, 3, 4, 5, 6, 7, 8,
//                        9, 17,
//                        18, 19, 20, 21, 22, 23, 24, 25, 26)
//                .apply(false, false, false)
//        );
//        return menu;
    }

//    public static ItemStack getChanceItem(int slot) {
//        int chance = Plugin.config.get().getInt("phases.loot.loot." + slot + ".chance", 100);
//        return new ItemBuilder(Material.COMPASS)
//                .setName("&bШанс появления")
//                .setLore(Arrays.asList(
//                        "&fШанс предмета: &a" + chance + "%",
//                        "&fНажмите &eЛКМ&f, чтобы увеличить на &e1",
//                        "&fНажмите &eПКМ&f, чтобы уменьшить на &e1",
//                        "&fНажмите &eShift + ЛКМ&f, чтобы увеличить на &e10",
//                        "&fНажмите &eShift + ПКМ&f, чтобы уменьшить на &e10"))
//                .build();
//    }


    @Override
    public void show(Player player) {
        getViewers().put(player, new Data());
        player.openInventory(inventory);
    }

    public void show(Player player, Data data) {
        getViewers().put(player, data);
        player.openInventory(inventory);
    }
}
