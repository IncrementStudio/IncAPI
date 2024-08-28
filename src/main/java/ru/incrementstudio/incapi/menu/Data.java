package ru.incrementstudio.incapi.menu;

import java.util.*;

public class Data {
    private final Map<String, List<?>> data = new HashMap<>();
    public List<?> getValue(String key) {
        if (!data.containsKey(key))
            data.put(key, new ArrayList<>());
        return data.get(key);
    }
    public void addValue(String key, List<?> value) {
        data.put(key, value);
    }
    public void removeValue(String key) {
        data.remove(key);
    }
}
