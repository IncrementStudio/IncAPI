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

    public static long toTicks(String timeMetric) {
        Matcher delayMatcher = Pattern.compile("^(\\d+)(\\w+)$").matcher(timeMetric);
        if (!delayMatcher.matches()) return 0;
        long delayValue = Long.parseLong(delayMatcher.group(1));
        String delayMetric = delayMatcher.group(2);
        if (delayMetric.equalsIgnoreCase("t") || delayMetric.equalsIgnoreCase("tick"))
            return delayValue;
        else if (delayMetric.equalsIgnoreCase("s") || delayMetric.equalsIgnoreCase("sec"))
            return delayValue * 20;
        else if (delayMetric.equalsIgnoreCase("m") || delayMetric.equalsIgnoreCase("min"))
            return delayValue * 20 * 60;
        return 0;
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
