package utils;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 * DateConverter utility for converting strings.
 */
public class DateConverter {
    /**
     * Converts a string to a date.
     * @param date The date string with the format YYYY-MM-DD.
     * @return The converted date object.
     */
    public static LocalDate convertStringToLocalDate(String date) {
        String[] array = date.split("-");
        LocalDate result = LocalDate.of(Integer.parseInt(array[0]), Integer.parseInt(array[1]),
                Integer.parseInt(array[2]));
        return result;
    }

    /**
     * Converts a string to local time.
     * @param time The time with format HH:MM.
     * @return The local time object.
     */
    public static LocalTime convertStringToLocalTime(String time) {
        String[] array = time.split(":");
        LocalTime result = LocalTime.of(Integer.parseInt(array[0]), Integer.parseInt(array[1]));
        return result;
    }
}
