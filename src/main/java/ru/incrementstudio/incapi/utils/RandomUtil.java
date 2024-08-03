package ru.incrementstudio.incapi.utils;

import java.util.List;
import java.util.Random;

public class RandomUtil {
    private static final Random random = new Random();

    public static void setSeed(long seed) {
        random.setSeed(seed);
    }

    public static double getDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public static float getFloat(float min, float max) {
        return (float) getDouble(min, max);
    }

    public static long getLong(long min, long max) {
        return (long) (min + (max - min) * Math.random());
    }

    public static int getInt(int min, int max) {
        return (int) getLong(min, max);
    }

    public static double getDouble() {
        return getDouble(Double.MIN_VALUE, Double.MAX_VALUE);
    }

    public static float getFloat() {
        return getFloat(Float.MIN_VALUE, Float.MAX_VALUE);
    }

    public static long getLong() {
        return getLong(Long.MIN_VALUE, Long.MAX_VALUE);
    }

    public static int getInt() {
        return getInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static <T> T getRandomFromList(List<T> list) {
        return list.get(random.nextInt(list.size()));
    }
}