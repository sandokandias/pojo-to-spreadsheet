package com.github.sandokandias.pojo.to.spreadsheet.format;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class LocalDateTimeFormat implements FieldFormatStrategy {

    @Override
    public Optional<String> format(Object value, Optional<String> pattern) {
        if (value == null) {
            return Optional.empty();
        }
        LocalDateTime localDateTime = (LocalDateTime) value;
        return Optional.of(DateTimeFormatter.ofPattern(pattern.get()).format(localDateTime));
    }

}
