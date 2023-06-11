package ru.incrementstudio.incrementstudioapi.utils;

import java.util.ArrayList;
import java.util.List;

public class ArrayUtil {
    public static String[] subarrayString(String[] array, int startIndex, int endIndex) {
        List<String> result = new ArrayList<>();
        for (int i = startIndex; i < endIndex && i < array.length; i++) {
            result.add(array[i]);
        }
        return result.toArray(new String[0]);
    }
}