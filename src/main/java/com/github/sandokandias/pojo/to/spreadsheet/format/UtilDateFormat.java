package com.github.sandokandias.pojo.to.spreadsheet.format;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

public class UtilDateFormat implements FieldFormatStrategy {

    @Override
    public Optional<String> format(Object value, Optional<String> pattern) {
        if (value == null) {
            return Optional.empty();
        }
        Date date = (Date) value;
        Instant instant = Instant.ofEpochMilli(date.getTime());
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
        return Optional.of(DateTimeFormatter.ofPattern(pattern.get()).format(localDateTime));
    }

}
