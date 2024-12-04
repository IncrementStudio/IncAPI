package ru.incrementstudio.incapi.util;

import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class ColoredString {
    private String string = "";
    private Color[] colors = new Color[]{Color.WHITE};
    private boolean bold = false;
    private boolean italic = false;
    private boolean underline = false;
    private boolean strikethrough = false;

    public ColoredString() {
    }

    public ColoredString(@NotNull String string) {
        this.string = string;
    }

    public ColoredString content(@NotNull String string) {
        this.string = string;
        return this;
    }

    public ColoredString color(@NotNull Color... colors) {
        if (colors.length == 0)
            this.colors = new Color[]{Color.WHITE};
        else
            this.colors = colors;
        return this;
    }

    public ColoredString bold(boolean bold) {
        this.bold = bold;
        return this;
    }

    public ColoredString italic(boolean italic) {
        this.italic = italic;
        return this;
    }

    public ColoredString underline(boolean underline) {
        this.underline = underline;
        return this;
    }

    public ColoredString striketrough(boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }

    @Override
    public String toString() {
        if (string == null) string = "";
        string = ColorUtil.disableColor(string);
        if (colors == null || colors.length == 0) colors = new Color[]{Color.WHITE};
        StringBuilder result = new StringBuilder();
        if (colors.length == 1) {
            Color color = colors[0];
            String hex = String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
            result.append(ColorUtil.simpleHex(hex));
            if (bold) result.append("&l");
            if (italic) result.append("&o");
            if (underline) result.append("&n");
            if (strikethrough) result.append("&m");
            result.append(string);
        } else {
            result.append(ColorUtil.toGradient(string, bold, italic, underline, strikethrough, colors));
        }
        return ColorUtil.toColor(result.toString());
    }
}
