package ru.incrementstudio.incapi.util;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {
    public static final String HEX_REGEX = "(#[a-fA-F0-9]{6})";

    public static String simpleHex(String string) {
        return toColor(string, Pattern.compile(HEX_REGEX));
    }
    public static String ampersandHex(String string) {
        return toColor(string, Pattern.compile("&" + HEX_REGEX));
    }
    public static String angleBracketsHex(String string) {
        return toColor(string, Pattern.compile("<" + HEX_REGEX + ">"));
    }
    public static String curlyBracketsHex(String string) {
        return toColor(string, Pattern.compile("\\{" + HEX_REGEX + "\\}"));
    }

    public static String toColor(String string) {
        return ChatColor.translateAlternateColorCodes('&', simpleHex(ampersandHex(angleBracketsHex(curlyBracketsHex(preprocess(string))))));
    }

    public static String toColor(String string, Pattern pattern) {
        Matcher matcher = pattern.matcher(string);
        return matcher.replaceAll(x -> {
            char[] ch = matcher.group(1).replace('#', 'x').toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch)
                builder.append("&").append(c);
            return builder.toString();
        });
    }

    private static String preprocess(String string) {
//        Pattern gradientPattern = Pattern.compile("&g\\{\\[(.+?)]\\(([0-9A-Fa-f#|]+)\\)}");
//        Matcher matcher = gradientPattern.matcher(string);
//        string = matcher.replaceAll(res -> {
//            String text = res.group(1);
//            Color[] colors = Arrays.stream(res.group(2).split("\\|"))
//                    .filter(x -> x.matches(HEX_REGEX))
//                    .map(Color::decode)
//                    .toArray(Color[]::new);
//            return toGradient(text, false, false, false, false, colors);
//        });
        return string;
    }

    public static List<String> toColor(List<String> list) {
        List<String> coloredList = new ArrayList<>();
        if (list != null)
            for (String string : list)
                coloredList.add(toColor(string));
        return coloredList;
    }

    public static String toOnlyFormat(char altColorChar, String textToTranslate) {
        Validate.notNull(textToTranslate, "Cannot translate null text");

        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && "LlMmNnOoRr".indexOf(b[i + 1]) > -1) {
                b[i] = ChatColor.COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

    public static String toOnlyColor(char altColorChar, String textToTranslate) {
        Validate.notNull(textToTranslate, "Cannot translate null text");

        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && "0123456789AaBbCcDdEeFfRr".indexOf(b[i + 1]) > -1) {
                b[i] = ChatColor.COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

    public static String toMagic(char altColorChar, String textToTranslate) {
        Validate.notNull(textToTranslate, "Cannot translate null text");

        char[] b = textToTranslate.toCharArray();
        for (int i = 0; i < b.length - 1; i++) {
            if (b[i] == altColorChar && "Kk".indexOf(b[i + 1]) > -1) {
                b[i] = ChatColor.COLOR_CHAR;
                b[i + 1] = Character.toLowerCase(b[i + 1]);
            }
        }
        return new String(b);
    }

    public static String disableColor(String string) {
        return disableColor('&', string);
    }

    public static String disableColor(char altColorChar, String string) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {
            if (string.charAt(i) == altColorChar) {
                i++;
                continue;
            }
            result.append(string.charAt(i));
        }
        return result.toString();
    }

    public static String toGradient(String string, boolean bold,
                                    boolean italic, boolean underline,
                                    boolean strikethrough, Color... colors) {
        StringBuilder result = new StringBuilder();
        double part = 1.0 / (colors.length - 1);
        for (int i = 0; i < string.length(); i++) {
            double lerper = MathUtil.inverseLerp(0, string.length(), i);
            int fromIndex = getFromColor(lerper, colors.length);
            int toIndex = getToColor(lerper, colors.length);
            Color fromColor = colors[fromIndex];
            Color toColor = colors[toIndex];
            double colorsLerper = MathUtil.inverseLerp(fromIndex * part, toIndex * part, lerper);
            double r = MathUtil.lerp(fromColor.getRed(), toColor.getRed(), colorsLerper);
            double g = MathUtil.lerp(fromColor.getGreen(), toColor.getGreen(), colorsLerper);
            double b = MathUtil.lerp(fromColor.getBlue(), toColor.getBlue(), colorsLerper);
            Color color = new Color((int)r, (int)g, (int)b);
            String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
            result.append(simpleHex(hex));
            if (bold) result.append("&l");
            if (italic) result.append("&o");
            if (underline) result.append("&n");
            if (strikethrough) result.append("&m");
            result.append(string.toCharArray()[i]);
        }
        return result.toString();
    }

    private static int getFromColor(double time, int colors) {
        for (int i = colors - 1; i >= 0; i--) {
            double part = 1.0 / (colors - 1);
            if (i * part <= time)
                return i;
        }
        return 0;
    }

    private static int getToColor(double time, int colors) {
        for (int i = 0; i < colors; i++) {
            double part = 1.0 / (colors - 1);
            if (i * part > time)
                return i;
        }
        return 0;
    }
}
