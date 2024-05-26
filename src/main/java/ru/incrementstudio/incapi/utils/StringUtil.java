package ru.incrementstudio.incapi.utils;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil {
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

    public static long fromTimeMetric(String string) {
        Matcher matcher = Pattern.compile("^(\\d+)(tick|t|sec|s|min|m|hour|h)$").matcher(string);
        if (matcher.matches()) {
            long value = Long.parseLong(matcher.group(1));
            String metric = matcher.group(2);
            if (metric.equalsIgnoreCase("tick") || metric.equalsIgnoreCase("t"))
                return value;
            else if (metric.equalsIgnoreCase("sec") || metric.equalsIgnoreCase("s"))
                return value * 20;
            else if (metric.equalsIgnoreCase("min") || metric.equalsIgnoreCase("m"))
                return value * 20 * 60;
            else if (metric.equalsIgnoreCase("hour") || metric.equalsIgnoreCase("h"))
                return value * 20 * 60 * 60;
        }
        return -1;
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
