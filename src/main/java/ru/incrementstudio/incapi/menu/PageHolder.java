package ru.incrementstudio.incapi.menu;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;

public class PageHolder implements InventoryHolder {
    private Page page;

    public Page getPage() {
        return page;
    }

    final void setPage(Page page) {
        this.page = page;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return getPage().getInventory();
    }
}