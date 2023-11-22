package ru.incrementstudio.incapi.menu;

import java.util.ArrayList;
import java.util.List;

public class MenuManager {

    private static MenuManager instance;

    private final List<Page> pages = new ArrayList<>();

    private MenuManager() {

    }
    public static MenuManager getInstance() {
        if (instance == null) instance = new MenuManager();
        return instance;
    }
}
