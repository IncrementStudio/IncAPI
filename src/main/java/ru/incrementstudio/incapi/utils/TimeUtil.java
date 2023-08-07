package ru.incrementstudio.incapi.utils;

import java.util.concurrent.TimeUnit;

public class TimeUtil {
    public static String toTime(int input) {
        String timeString = null;
        int days = (int) TimeUnit.SECONDS.toDays(input);
        int hours = (int) (TimeUnit.SECONDS.toHours(input) - TimeUnit.DAYS.toHours(days));
        int minutes = (int) (TimeUnit.SECONDS.toMinutes(input) - TimeUnit.HOURS.toMinutes(hours) - TimeUnit.DAYS.toMinutes(days));
        int seconds = (int) (TimeUnit.SECONDS.toSeconds(input) - TimeUnit.MINUTES.toSeconds(minutes)            - TimeUnit.HOURS.toSeconds(hours) - TimeUnit.DAYS.toSeconds(days));

        if (days == 0){
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

    private static String setCorrectForm(int time, TimeType type){
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
