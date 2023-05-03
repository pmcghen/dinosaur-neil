package com.example.dinosaurneil.util;

import java.time.LocalDateTime;

public class StringToLocalDateTime {

    public static LocalDateTime convertStringToDateTime(String dateString) {
        int monthNumber = convertMonthStringToInt(dateString.substring(8, 11));
        int day = Integer.parseInt(dateString.substring(5, 7));
        int year = Integer.parseInt(dateString.substring(12, 16));
        int hour = Integer.parseInt(dateString.substring(17, 19));
        int minute = Integer.parseInt(dateString.substring(20, 22));
        int second = Integer.parseInt(dateString.substring(23, 25));

        return LocalDateTime.of(year, monthNumber, day, hour, minute, second);
    }

    private static int convertMonthStringToInt(String month) {
        if (month.equalsIgnoreCase("jan")) {
            return 1;
        }

        if (month.equalsIgnoreCase("feb")) {
            return 2;
        }

        if (month.equalsIgnoreCase("mar")) {
            return 3;
        }

        if (month.equalsIgnoreCase("apr")) {
            return 4;
        }

        if (month.equalsIgnoreCase("may")) {
            return 5;
        }

        if (month.equalsIgnoreCase("jun")) {
            return 6;
        }

        if (month.equalsIgnoreCase("jul")) {
            return 7;
        }

        if (month.equalsIgnoreCase("aug")) {
            return 8;
        }

        if (month.equalsIgnoreCase("sep")) {
            return 9;
        }

        if (month.equalsIgnoreCase("oct")) {
            return 10;
        }

        if (month.equalsIgnoreCase("nov")) {
            return 11;
        }

        if (month.equalsIgnoreCase("dec")) {
            return 12;
        }

        return 0;
    }
}
