package ru.incrementstudio.incapi.utils;

import org.apache.commons.lang.Validate;
import org.bukkit.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ColorUtil {
    public static String toColor(String string) {
        StringBuilder result = new StringBuilder();
        result.append(string);

        Pattern pattern1 = Pattern.compile("\\{&#[a-fA-F0-9]{6}}");
        Pattern pattern2 = Pattern.compile("\\{#[a-fA-F0-9]{6}}");
        Pattern pattern3 = Pattern.compile("<#[a-fA-F0-9]{6}>");
        Pattern pattern4 = Pattern.compile("&#[a-fA-F0-9]{6}");
        Pattern pattern5 = Pattern.compile("#[a-fA-F0-9]{6}");

        Matcher matcher1 = pattern1.matcher(result);
        while (matcher1.find()) {
            String hexCode = result.substring(matcher1.start(), matcher1.end());
            String replaceSharp = hexCode.replace("{", "").replace("&", "").replace('#', 'x').replace("}", "");
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }
            result.replace(matcher1.start(), matcher1.end(), builder.toString());
            matcher1 = pattern1.matcher(result);
        }

        Matcher matcher2 = pattern2.matcher(result);
        while (matcher2.find()) {
            String hexCode = result.substring(matcher2.start(), matcher2.end());
            String replaceSharp = hexCode.replace("{", "").replace('#', 'x').replace("}", "");
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }
            result.replace(matcher2.start(), matcher2.end(), builder.toString());
            matcher2 = pattern2.matcher(result);
        }

        Matcher matcher3 = pattern3.matcher(result);
        while (matcher3.find()) {
            String hexCode = result.substring(matcher3.start(), matcher3.end());
            String replaceSharp = hexCode.replace("<", "").replace('#', 'x').replace(">", "");
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }
            result.replace(matcher3.start(), matcher3.end(), builder.toString());
            matcher3 = pattern3.matcher(result);
        }

        Matcher matcher4 = pattern4.matcher(result);
        while (matcher4.find()) {
            String hexCode = result.substring(matcher4.start(), matcher4.end());
            String replaceSharp = hexCode.replace("&", "").replace('#', 'x');
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }
            result.replace(matcher4.start(), matcher4.end(), builder.toString());
            matcher4 = pattern4.matcher(result);
        }

        Matcher matcher5 = pattern5.matcher(result);
        while (matcher5.find()) {
            String hexCode = result.substring(matcher5.start(), matcher5.end());
            String replaceSharp = hexCode.replace('#', 'x');
            char[] ch = replaceSharp.toCharArray();
            StringBuilder builder = new StringBuilder();
            for (char c : ch) {
                builder.append("&").append(c);
            }
            result.replace(matcher5.start(), matcher5.end(), builder.toString());
            matcher5 = pattern5.matcher(result);
        }
        return ChatColor.translateAlternateColorCodes('&', result.toString());
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
            if (string.toCharArray()[i] == '&' || string.toCharArray()[i] == '§') {
                string = string.replaceFirst(String.valueOf(string.toCharArray()[i]) + string.toCharArray()[i + 1], "");
                i--;
            }
        }
        return string;
    }
}
