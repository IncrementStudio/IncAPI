package ru.incrementstudio.incapi.quantum;

import java.util.*;
import java.util.function.Predicate;

public class Quantum {
    private final Map<Integer, Predicate<byte[]>> listeners = new HashMap<>();
    public Map<Integer, Predicate<byte[]>> getListeners() { return listeners; }

    public void setListener(int id, Predicate<byte[]> listener) {
        listeners.put(id, listener);
    }

    public void send(int id, byte[] item) {
        Predicate<byte[]> listener = listeners.get(id);
        listener.test(item);
    }
}
