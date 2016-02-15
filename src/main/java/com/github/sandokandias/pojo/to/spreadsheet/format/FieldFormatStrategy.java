package com.github.sandokandias.pojo.to.spreadsheet.format;

import java.util.Optional;

public interface FieldFormatStrategy {

    Optional<String> format(Object value, Optional<String> pattern);

}
