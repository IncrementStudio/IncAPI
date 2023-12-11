package ru.incrementstudio.incapi.menu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
    private final Map<String, List<Object>> data = new HashMap<>();
    public List<Object> getData(String key) {
        return data.get(key);
    }
    public void removeData(String key) {
        data.remove(key);
    }
    public void setData(String key, Object... values) {
        data.put(key, Arrays.asList(values));
    }
}
