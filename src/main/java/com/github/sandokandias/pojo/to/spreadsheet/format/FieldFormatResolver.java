package com.github.sandokandias.pojo.to.spreadsheet.format;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public final class FieldFormatResolver {

    private final static Map<Class<?>, FieldFormatStrategy> STRATEGIES = new HashMap<>();

    private FieldFormatResolver() {}

    static {
        STRATEGIES.put(Date.class, new UtilDateFormat());
        STRATEGIES.put(Timestamp.class, new TimestampFormat());
        STRATEGIES.put(java.sql.Date.class, new SqlDateFormat());
        STRATEGIES.put(LocalDate.class, new LocalDateFormat());
        STRATEGIES.put(LocalDateTime.class, new LocalDateTimeFormat());
        STRATEGIES.put(BigDecimal.class, new MoneyFormat());
        STRATEGIES.put(String.class, new TextFormat());
    }

    public static FieldFormatStrategy getStrategy(Class<?> dateClass) {
        return STRATEGIES.get(dateClass);
    }

    public static FieldFormatStrategy getStrategy(Object dateInstance) {
        return STRATEGIES.get(dateInstance.getClass());
    }
}
