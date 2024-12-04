package ru.incrementstudio.incapi.util;

public class MathUtil {
    public static double lerp(double a, double b, double t) {
        return a * (1 - t) + b * t;
    }
    public static double inverseLerp(double a, double b, double t) {
        return (t - a) / (b - a);
    }
}
