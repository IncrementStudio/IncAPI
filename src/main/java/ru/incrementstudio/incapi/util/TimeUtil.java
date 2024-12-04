package ru.incrementstudio.incapi.util;

import java.util.concurrent.TimeUnit;

public class TimeUtil {
    public static long secondsFromString(String string) {
        long second = 0;
        char lastChar = string.charAt(string.length() - 1);
        char firstChar = string.charAt(0);
        if (Character.isDigit(lastChar) || Character.isLetter(firstChar)) {
            return 0;
        }
        int i = 0;
        int size = string.length();
        for (; i < size; i++) {
            char charr = string.charAt(i);
            if (Character.isDigit(charr)) {
                long secondd = 0;
                int sizee = i;
                while (Character.isDigit(string.charAt(sizee))) {
                    if (secondd > Integer.MAX_VALUE) {
                        return -1;
                    }
                    secondd = 10 * secondd + Character.getNumericValue(string.charAt(sizee));
                    sizee++;
                }
                if (sizee < size - 1) {
                    if (Character.isLetter(string.charAt(sizee + 1))) {
                        return 0;
                    }
                }
                if (String.valueOf(string.charAt(sizee)).equalsIgnoreCase("y") || String.valueOf(string.charAt(sizee)).equalsIgnoreCase("г")) {
                    if ((secondd * 31536000L) > Integer.MAX_VALUE) {
                        return -1;
                    }
                    second += secondd * 31536000L;
                } else if (String.valueOf(string.charAt(sizee)).equalsIgnoreCase("w") || String.valueOf(string.charAt(sizee)).equalsIgnoreCase("н")) {
                    if ((secondd * 604800L) > Integer.MAX_VALUE) {
                        return -1;
                    }
                    second += secondd * 604800L;
                } else if (String.valueOf(string.charAt(sizee)).equalsIgnoreCase("d") || String.valueOf(string.charAt(sizee)).equalsIgnoreCase("д")) {
                    if ((secondd * 86400L) > Integer.MAX_VALUE) {
                        return -1;
                    }
                    second += secondd * 86400L;
                } else if (String.valueOf(string.charAt(sizee)).equalsIgnoreCase("h") || String.valueOf(string.charAt(sizee)).equalsIgnoreCase("ч")) {
                    if ((secondd * 3600L) > Integer.MAX_VALUE) {
                        return -1;
                    }
                    second += secondd * 3600L;
                } else if (String.valueOf(string.charAt(sizee)).equalsIgnoreCase("m") || String.valueOf(string.charAt(sizee)).equalsIgnoreCase("м")) {
                    if ((secondd * 60L) > Integer.MAX_VALUE) {
                        return -1;
                    }
                    second += secondd * 60L;
                } else if (String.valueOf(string.charAt(sizee)).equalsIgnoreCase("s") || String.valueOf(string.charAt(sizee)).equalsIgnoreCase("с")) {
                    second += secondd;
                } else {
                    return 0;
                }
            }
        }
        return second;
    }
    public static String toTime(long input) {
        String timeString = null;
        long days = TimeUnit.SECONDS.toDays(input);
        long hours = (TimeUnit.SECONDS.toHours(input) - TimeUnit.DAYS.toHours(days));
        long minutes = (TimeUnit.SECONDS.toMinutes(input) - TimeUnit.HOURS.toMinutes(hours) - TimeUnit.DAYS.toMinutes(days));
        long seconds = (TimeUnit.SECONDS.toSeconds(input) - TimeUnit.MINUTES.toSeconds(minutes)            - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.DAYS.toSeconds(days));

        if (days == 0) {
            if (hours != 0 && minutes != 0 && seconds == 0) {
                timeString = setCorrectForm(hours, TimeType.HOUR) + " и " + setCorrectForm(minutes, TimeType.MINUTE);
            }
            if (hours != 0 && minutes == 0 && seconds == 0) {
                timeString = setCorrectForm(hours, TimeType.HOUR);
            }
            if (hours != 0 && minutes == 0 && seconds != 0) {
                timeString = setCorrectForm(hours, TimeType.HOUR) + ", 0 минут и " + setCorrectForm(seconds, TimeType.SECOND);
            }
            if (seconds != 0 && minutes != 0 && hours != 0) {
                timeString = setCorrectForm(hours, TimeType.HOUR) + ", " + setCorrectForm(minutes, TimeType.MINUTE)
                        + " и " + setCorrectForm(seconds, TimeType.SECOND);
            }
            if (hours == 0){
                if (minutes != 0 && seconds == 0) {
                    timeString = setCorrectForm(minutes, TimeType.MINUTE);
                }
                if (minutes != 0 && seconds != 0) {
                    timeString = setCorrectForm(minutes, TimeType.MINUTE) + " и " + setCorrectForm(seconds, TimeType.SECOND);
                }
                if (minutes == 0 && seconds != 0){
                    timeString = setCorrectForm(seconds, TimeType.SECOND);
                }
                if (minutes == 0 && seconds == 0){
                    timeString = "меньше секунды";
                }
            }
        } else {
            if (seconds == 0) {
                timeString = setCorrectForm(days, TimeType.DAY) + ", " + setCorrectForm(hours, TimeType.HOUR) +
                        ", " + setCorrectForm(minutes, TimeType.MINUTE) + " и 0 секунд";
            } else {
                timeString = setCorrectForm(days, TimeType.DAY) + ", " + setCorrectForm(hours, TimeType.HOUR) +
                        ", " + setCorrectForm(minutes, TimeType.MINUTE) + " и " + setCorrectForm(seconds, TimeType.SECOND);
            }
        }
        return timeString;
    }

    private static String setCorrectForm(long time, TimeType type){
        String string = "";
        if ((time > 4 && time < 21) || (time > 24 && time < 31) || (time > 34 && time < 41) ||
                (time > 44 && time < 51) || time > 54 || time == 0) {

            if (type.equals(TimeType.SECOND)) {
                if (time == 0) {
                    string = "меньше секунды";
                } else {
                    string = time + " секунд";
                }
            }
            if (type.equals(TimeType.MINUTE)) {
                string = time + " минут";
            }
            if (type.equals(TimeType.HOUR)) {
                string = time + " часов";
            }
            if (type.equals(TimeType.DAY)) {
                string = time + " дней";
            }
        } else if (time == 22 || time == 4 || time == 3 || time == 2 || time == 23 ||
                time == 24 || time == 32 || time == 33 || time == 34 || time == 42 ||
                time == 43 || time == 44 || time == 52 || time == 53 || time == 54) {

            if (type.equals(TimeType.SECOND)) {
                string = time + " секунды";
            }
            if (type.equals(TimeType.MINUTE)) {
                string = time + " минуты";
            }
            if (type.equals(TimeType.HOUR)) {
                string = time + " часа";
            }
            if (type.equals(TimeType.DAY)) {
                string = time + " дня";
            }
        } else {
            if (type.equals(TimeType.SECOND)) {
                string = time + " секунду";
            }
            if (type.equals(TimeType.MINUTE)) {
                string = time + " минуту";
            }
            if (type.equals(TimeType.HOUR)) {
                string = time + " час";
            }
            if (type.equals(TimeType.DAY)) {
                string = time + " день";
            }
        }
        return string;
    }

    public enum TimeType{
        SECOND,
        MINUTE,
        HOUR,
        DAY;
    }
}
