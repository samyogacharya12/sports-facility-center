package util;

import lombok.experimental.UtilityClass;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@UtilityClass
public class DateUtil {


    public static String convertTo24Hours(String input) {
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("HH:mm");
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        LocalTime time = LocalTime.parse(input, inputFormatter);
        return time.format(outputFormatter);
    }
}
