package ru.incrementstudio.incapi.utils;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

public class StringUtil {
    public static String repeat(String pattern, int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(pattern);
        }
        return result.toString();
    }

    public static String getStringFromString(String value) {
        if (value.contains(" %: ")) {
            String[] values = value.split(" %: ");
            return (String) RandomUtil.getRandomFromList(Arrays.stream(values).collect(Collectors.toList()));
        } else {
            return value;
        }
    }

    public static boolean getBooleanFromString(String value) {
        return Boolean.parseBoolean(getStringFromString(value));
    }

    public static int getIntFromString(String value) {
        value = getStringFromString(value);
        if (value.contains(" %! ")) {
            String[] values = value.split(" %! ");
            if (values.length != 2) return 0;
            return RandomUtil.getInt(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
        } else {
            return Integer.parseInt(value);
        }
    }

    public static double getDoubleFromString(String value) {
        value = getStringFromString(value);
        if (value.contains(" %! ")) {
            String[] values = value.split(" %! ");
            if (values.length != 2) return 0;
            return RandomUtil.getDouble(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
        } else {
            return Double.parseDouble(value);
        }
    }

    public static String getRandomString(int length) {
        String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }
}
