package ru.incrementstudio.incrementstudioapi.utils;

public class StringUtil {
    public static String repeat(String pattern, int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result += pattern;
        }
        return result;
    }
}
