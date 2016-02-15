package com.github.sandokandias.pojo.to.spreadsheet.format;

import java.text.ParseException;
import java.util.Optional;

import javax.swing.text.MaskFormatter;

public class TextFormat implements FieldFormatStrategy {

    @Override
    public Optional<String> format(Object value, Optional<String> pattern) {

        try {
            if (value == null) {
                return Optional.empty();
            }
            if (pattern == null || !pattern.isPresent()) {
                return Optional.of(value.toString());
            }
            MaskFormatter formatter = new MaskFormatter(pattern.get());
            formatter.setValueContainsLiteralCharacters(false);
            return Optional.of(formatter.valueToString(value));
        } catch (ParseException e) {
            return Optional.of(value.toString());
        }
    }

}
