package ru.incrementstudio.incrementstudioapi.utils;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;

public class ColorUtil {
    public static String toColor(String string) {
        String coloredString = null;
        if (string != null) {
            coloredString = ChatColor.translateAlternateColorCodes('&', string);
        }
        return coloredString;
    }

    public static List<String> toColor(List<String> list) {
        List<String> coloredList = new ArrayList<>();
        if (list != null) {
            for (String string : list) {
                coloredList.add(toColor(string));
            }
        }
        return coloredList;
    }
    public static String toOnlyFormat(char altColorChar, String textToTranslate) {
        Validate.notNull(textToTranslate, "Невозможно перевести нулевой текст");

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
        Validate.notNull(textToTranslate, "Невозможно перевести нулевой текст");

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
        Validate.notNull(textToTranslate, "Невозможно перевести нулевой текст");

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
        for (int i = 0; i < string.length(); i++) {
            if (string.toCharArray()[i] == '&') {
                string = string.replaceFirst(String.valueOf(string.toCharArray()[i]) + string.toCharArray()[i + 1], "");
                i--;
            }
        }
        return string;
    }
}
