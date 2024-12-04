package ru.incrementstudio.incapi.menu;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class ViewersMap extends HashMap<Player, Data> {
    public ViewersMap() {
    }

    public ViewersMap(ViewersMap map) {
        super(map);
    }
}