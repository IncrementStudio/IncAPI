package ru.incrementstudio.incapi.utils;

public class MathUtil {
    public static double lerp(double a, double b, double t) {
        return a * (1 - t) + b * t;
    }

    public static double inverseLerp(double a, double b, double t) {
        return (t - a) / (b - a);
    }

    public static double clamp(double min, double max, double value) {
        return Math.max(min, Math.min(max, value));
    }

    public static float clamp(float min, float max, float value) {
        return Math.max(min, Math.min(max, value));
    }

    public static int clamp(int min, int max, int value) {
        return Math.max(min, Math.min(max, value));
    }

    public static long clamp(long min, long max, long value) {
        return Math.max(min, Math.min(max, value));
    }
}