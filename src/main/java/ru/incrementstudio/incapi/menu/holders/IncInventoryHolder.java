package ru.incrementstudio.incapi.menu.holders;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.jetbrains.annotations.NotNull;
import ru.incrementstudio.incapi.menu.Data;
import ru.incrementstudio.incapi.menu.menus.Menu;

public class IncInventoryHolder implements InventoryHolder {
    private final Menu menu;
    private final boolean canClick;
    private final boolean canDrag;
    private final boolean canDrop;
    private Data data;

    public IncInventoryHolder(boolean canClick, boolean canDrag, boolean canDrop, Menu menu) {
        this.menu = menu;
        this.canClick = canClick;
        this.canDrag = canDrag;
        this.canDrop = canDrop;
    }

    public IncInventoryHolder(boolean canClick, boolean canDrag, boolean canDrop, Data data, Menu menu) {
        this.menu = menu;
        this.canClick = canClick;
        this.canDrag = canDrag;
        this.canDrop = canDrop;
        this.data = data;
    }

    public boolean isCanClick() {
        return canClick;
    }

    public boolean isCanDrag() {
        return canDrag;
    }

    public boolean isCanDrop() {
        return canDrop;
    }

    public Data getData() {
        return data;
    }

    public Menu getMenu() {
        return menu;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return null;
    }
}
