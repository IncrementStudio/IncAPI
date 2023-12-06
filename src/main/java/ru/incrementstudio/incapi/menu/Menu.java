package ru.incrementstudio.incapi.menu;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private final List<Page> pages = new ArrayList<>();
    public List<Page> getPages() {
        return pages;
    }
    public Page getPage(int index) {
        return pages.get(index);
    }

    public void show(Player player) {
        getPage(0).show(player);
    }
    public void show(Player player, int page) {
        getPage(page).show(player);
    }
//    public void show(Player player, int page, Data data) {
//        pages.get(page).show(player, data);
//    }
//
//    public void show(Player player, int page, Data data, Consumer<Data> start, Consumer<Data> end) {
//        pages.get(page).show(player, data, start, end);
//    }

    public Menu addPage(Page page) {
        pages.add(page);
        return this;
    }
    public Menu insertPage(Page page, int index) {
        pages.add(index, page);
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
}
