package ru.incrementstudio.incapi.menu.holders.impl;

import ru.incrementstudio.incapi.menu.holders.IncInventoryHolder;
import ru.incrementstudio.incapi.menu.menus.impl.loot.LootMenu;

public class LootInventoryHolder extends IncInventoryHolder {
    private final LootMenu lootMenu;

    public LootInventoryHolder(boolean canClick, boolean canDrag, boolean canDrop, LootMenu lootMenu) {
        super(canClick, canDrag, canDrop, lootMenu);
        this.lootMenu = lootMenu;
    }

    public LootMenu getMenu() {
        return lootMenu;
    }
}
