package ru.incrementstudio.incrementstudioapi.utils;

public class StringUtil {
    public static String repeat(String pattern, int count) {
        String result = "";
        for (int i = 0; i < count; i++) {
            result += pattern;
        }
        return result;
    }

    public static String stringWithoutColorTags(String string) {
        for (int i = 0; i < string.length(); i++) {
            if (string.toCharArray()[i] == '&') {
                string = string.replaceFirst(String.valueOf(string.toCharArray()[i]) + string.toCharArray()[i + 1], "");
                i--;
            }
        }
        return string;
    }
}
