package ru.incrementstudio.incapi.menu.menus;


import org.bukkit.entity.Player;
import ru.incrementstudio.incapi.menu.Page;
import ru.incrementstudio.incapi.menu.ViewersMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class MultiPageMenu extends Menu {
    private final List<Page> pages = new ArrayList<>();

    public MultiPageMenu(Page... pages) {
        this(Arrays.asList(pages));
    }

    public MultiPageMenu(List<Page> pages) {
        for (Page page : pages) {
            if (page.onAdd(this))
                this.pages.add(page);
        }
    }

    public Map<Page, ViewersMap> getViewers() {
        return pages.stream()
                .collect(Collectors.toMap(
                        k -> k,
                        Page::getViewers
                ));
    }

    public List<Page> getPages() {
        return pages;
    }

    public Page getPage(int index) {
        return pages.get(index);
    }

    public MultiPageMenu open(Player player) {
        pages.get(0).open(player);
        return this;
    }

    public MultiPageMenu open(Player player, int page) {
        pages.get(page).open(player);
        return this;
    }

    public MultiPageMenu addPage(Page page) {
        if (page.onAdd(this))
            pages.add(page);
        return this;
    }

    public MultiPageMenu insertPage(Page page, int index) {
        if (page.onAdd(this))
            pages.add(index, page);
        return this;
    }

    public MultiPageMenu removePage(int index) {
        pages.remove(index);
        return this;
    }

    public MultiPageMenu clearPages() {
        pages.clear();
        return this;
    }
}