package com.github.sandokandias.pojo.to.spreadsheet.format;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class LocalDateFormat implements FieldFormatStrategy {

    @Override
    public Optional<String> format(Object value, Optional<String> pattern) {
        if (value == null) {
            return Optional.empty();
        }
        LocalDate localDate = (LocalDate) value;
        return Optional.of(DateTimeFormatter.ofPattern(pattern.get()).format(localDate));
    }

}
