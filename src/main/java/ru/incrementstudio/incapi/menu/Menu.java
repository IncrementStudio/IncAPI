package ru.incrementstudio.incapi.menu;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.incrementstudio.incapi.utils.MenuUtil;

public abstract class Menu {
    private final Inventory inventory;
    private final int page;
    private final Player player;
    private String data;

    public Menu(String title, int size, int page, Player player) {
        this.page = page;
        this.player = player;
        inventory = Bukkit.createInventory(null, size, title);
        fillBorders();
        fill();
    }

    public Menu(String title, int size, int page, Player player, String data) {
        this.page = page;
        this.player = player;
        this.data = data;
        inventory = Bukkit.createInventory(null, size, title);
        fillBorders();
        fill();
    }

    protected void fillBorders() {
        for (int i = 0; i < inventory.getSize(); i++) {
            if ((i < 9 || i > inventory.getSize() - 10) || (((i + 1) % 9) == 0 || (i % 9) == 0)) {
                if (inventory.getItem(i) == null) {
                    inventory.setItem(i, MenuUtil.getBorderItem());
                }
            }
        }
    }

    public abstract void fill();
    public Inventory getInventory() {
        return inventory;
    }

    public int getPage() {
        return page;
    }

    public Player getPlayer() {
        return player;
    }

    public String getData() {
        return data;
    }
}
