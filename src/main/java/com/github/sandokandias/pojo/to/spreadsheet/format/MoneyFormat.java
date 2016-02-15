package com.github.sandokandias.pojo.to.spreadsheet.format;

import java.text.NumberFormat;
import java.util.Optional;

import com.github.sandokandias.pojo.to.spreadsheet.utils.LocaleUtils;

public class MoneyFormat implements FieldFormatStrategy {

    @Override
    public Optional<String> format(Object value, Optional<String> pattern) {

        Number money = (Number) value;
        if (money == null) {
            return Optional.empty();
        }
        NumberFormat ptBRFormat = NumberFormat.getCurrencyInstance(LocaleUtils.PT_BR);
        return Optional.of(ptBRFormat.format(money));
    }

}
