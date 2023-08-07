package ru.incrementstudio.incapi.utils;

import java.util.List;
import java.util.Random;

public class RandomUtil {
    private static Random random = new Random();
    public static void setSeed(long seed) {
        random.setSeed(seed);
    }
    public static double getDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }
    public static int getInt(int min, int max) {
        return (int) (min + (max - min) * Math.random());
    }
    public static Object getRandomFromList(List<?> list) {
        return list.get(random.nextInt(list.size()));
    }
}
