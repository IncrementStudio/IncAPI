package ru.incrementstudio.incapi.menu.holders.impl;

import ru.incrementstudio.incapi.menu.Data;
import ru.incrementstudio.incapi.menu.holders.IncInventoryHolder;
import ru.incrementstudio.incapi.menu.menus.impl.loot.LootMenu;
import ru.incrementstudio.incapi.menu.menus.impl.loot.LootMenuType;

public class LootInventoryHolder extends IncInventoryHolder {
    private final LootMenu lootMenu;
    private final LootMenuType lootMenuType;

    public LootInventoryHolder(boolean canClick, boolean canDrag, boolean canDrop, LootMenu lootMenu, LootMenuType lootMenuType) {
        super(canClick, canDrag, canDrop, lootMenu);
        this.lootMenu = lootMenu;
        this.lootMenuType = lootMenuType;
    }

    public LootInventoryHolder(boolean canClick, boolean canDrag, boolean canDrop, LootMenu lootMenu, LootMenuType lootMenuType, Data data) {
        super(canClick, canDrag, canDrop, lootMenu, data);
        this.lootMenu = lootMenu;
        this.lootMenuType = lootMenuType;
    }
    public LootMenu getMenu() {
        return lootMenu;
    }

    public LootMenuType getLootMenuType() {
        return lootMenuType;
    }
}
