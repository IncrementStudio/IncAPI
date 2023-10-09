package ru.incrementstudio.incapi.menu;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Menu {
    private final List<Page> pages = new ArrayList<>();

    public Menu() {
        MenuListener.menus.add(this);
    }

    public void show(Player player) {
        pages.get(0).show(player);
    }

    public void show(Player player, int page) {
        pages.get(page).show(player);
    }

    public void show(Player player, Data data) {
        pages.get(0).show(player);
    }

    public void show(Player player, int page, Data data) {
        pages.get(page).show(player, data);
    }

    public void show(Player player, int page, Data data, Consumer<Data> start, Consumer<Data> end) {
        pages.get(page).show(player, data, start, end);
    }


    public Menu addPage(Page page) {
        pages.add(page);
        return this;
    }

    public Menu setPage(int index, Page page) {
        pages.set(index, page);
        return this;
    }

    public Menu removePage(Page page) {
        pages.remove(page);
        return this;
    }

    public Menu removePage(int index) {
        pages.remove(index);
        return this;
    }

    public Menu clearPages() {
        pages.clear();
        return this;
    }

    public Page getPage(int index) {
        return pages.get(index);
    }

    public List<Page> getPages() {
        return pages;
    }
}
