package ru.incrementstudio.incapi.util;

import java.util.Arrays;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StringUtil {
    public static String repeat(String pattern, int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(pattern);
        }
        return result.toString();
    }

    public static long fromTimeMetric(String string) {
        Matcher matcher = Pattern.compile("^(\\d+)(\\w+)$").matcher(string);
        if (matcher.matches()) {
            long value = Long.parseLong(matcher.group(1));
            String metric = matcher.group(2).toLowerCase();
            switch (metric) {
                case "ticks":
                case "tick":
                case "t":
                    return value;
                case "seconds":
                case "second":
                case "sec":
                case "s":
                    return value * 20L;
                case "minutes":
                case "minute":
                case "min":
                case "m":
                    return value * 20L * 60L;
                case "hours":
                case "hour":
                case "h":
                    return value * 20L * 60L * 60L;
                case "days":
                case "day":
                case "d":
                    return value * 20L * 60L * 60L * 24L;
                case "weeks":
                case "week":
                case "w":
                    return value * 20L * 60L * 60L * 24L * 7L;
                case "months":
                case "month":
                case "mon":
                    return value * 20L * 60L * 60L * 24L * 30L;
                case "years":
                case "year":
                case "y":
                    return value * 20L * 60L * 60L * 24L * 365L;
            }
        }
        return -1L;
    }

//    public static String getStringFromString(String value) {
//        if (value.contains(" %: ")) {
//            String[] values = value.split(" %: ");
//            return (String) RandomUtil.getRandomFromList(Arrays.stream(values).collect(Collectors.toList()));
//        } else {
//            return value;
//        }
//    }
//
//    public static boolean getBooleanFromString(String value) {
//        return Boolean.parseBoolean(getStringFromString(value));
//    }
//
//    public static int getIntFromString(String value) {
//        value = getStringFromString(value);
//        if (value.contains(" %! ")) {
//            String[] values = value.split(" %! ");
//            if (values.length != 2) return 0;
//            return RandomUtil.getInt(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
//        } else {
//            return Integer.parseInt(value);
//        }
//    }
//
//    public static double getDoubleFromString(String value) {
//        value = getStringFromString(value);
//        if (value.contains(" %! ")) {
//            String[] values = value.split(" %! ");
//            if (values.length != 2) return 0;
//            return RandomUtil.getDouble(Double.parseDouble(values[0]), Double.parseDouble(values[1]));
//        } else {
//            return Double.parseDouble(value);
//        }
//    }

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
