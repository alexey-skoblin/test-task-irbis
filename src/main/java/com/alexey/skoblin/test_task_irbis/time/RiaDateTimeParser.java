package com.alexey.skoblin.test_task_irbis.time;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;
import java.util.Objects;
import org.springframework.stereotype.Component;

@Component
public class RiaDateTimeParser {

    public LocalDateTime parse(String url, String timeString) throws DateTimeParseException {
        if (Objects.equals(timeString, "")) {
            if (!url.matches(".+\\d\\d\\d\\d\\d\\d\\d\\d.*")) {
                throw new DateTimeParseException("Invalid date format: " + url, url, 0);
            }
            while (!url.matches("^\\d\\d\\d\\d\\d\\d\\d\\d.*")) {
                url = url.substring(1);
            }
            String[] urlParts = url.split("/");
            return LocalDateTime.parse(
                    String.format("%1$s-%2$s-%3$sT00:00",
                            urlParts[0],
                            urlParts[1],
                            urlParts[2]
                    )
            );
        }
        if (timeString.length() == 5) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime localTime = LocalTime.parse(timeString, formatter);
            return localTime.atDate(LocalDateTime.now().toLocalDate());
        }
        if (timeString.startsWith("Вчера, ")){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime localTime = LocalTime.parse(timeString.substring(8), formatter);
            return localTime.atDate(LocalDateTime.now().toLocalDate().minusDays(1));
        }
        Locale locale = new Locale("ru");
        DateTimeFormatter formatter = DateTimeFormatter
                .ofPattern("d MMMM, HH:mm", locale);
        return LocalDateTime.parse(timeString, formatter);
    }
}
