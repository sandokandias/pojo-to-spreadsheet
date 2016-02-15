package com.github.sandokandias.pojo.to.spreadsheet;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.sandokandias.pojo.to.spreadsheet.format.FieldFormatResolver;
import com.github.sandokandias.pojo.to.spreadsheet.format.FieldFormatStrategy;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelCell {

    CellType type() default CellType.TEXT;

    int order();

    String label() default "";

    String formatPattern() default "";

    public static enum CellType {

        TEXT {
            @Override
            public HSSFCell createCell(ExcelCellData data) {
                try {
                    HSSFCell cell = data.row.createCell(data.config.order());
                    Object value = data.field.get(data.item);
                    Optional<String> cellValue = Optional.empty();
                    if (value != null) {
                        FieldFormatStrategy strategy = FieldFormatResolver.getStrategy(value.getClass());
                        cellValue = strategy.format(value, Optional.of(data.config.formatPattern()));
                    }
                    cell.setCellValue(cellValue.orElse(""));
                    return cell;
                } catch (Exception e) {
                    LOG.error("Error to create cell text type.", e);
                    throw new RuntimeException(e);
                }
            }
        },
        DATE {
            @Override
            public HSSFCell createCell(ExcelCellData data) {
                try {
                    HSSFCell cell = data.row.createCell(data.config.order());
                    Object value = data.field.get(data.item);
                    Optional<String> cellValue = Optional.empty();
                    if (value != null) {
                        FieldFormatStrategy strategy = FieldFormatResolver.getStrategy(value.getClass());
                        cellValue = strategy.format(value, Optional.of(data.config.formatPattern()));
                    }
                    cell.setCellValue(cellValue.orElse(""));
                    return cell;
                } catch (Exception e) {
                    LOG.error("Error to create cell date type.", e);
                    throw new RuntimeException(e);
                }
            }
        },
        NUMBER {
            @Override
            public HSSFCell createCell(ExcelCellData data) {
                try {
                    HSSFCell cell = data.row.createCell(data.config.order());
                    Object value = data.field.get(data.item);
                    Optional<Double> cellValue = Optional.empty();
                    if (value != null) {
                        cellValue = Optional.of(((Number) value).doubleValue());
                    }
                    cell.setCellValue(cellValue.orElse(0D));
                    return cell;
                } catch (Exception e) {
                    LOG.error("Error to create cell number type.", e);
                    throw new RuntimeException(e);
                }
            }
        },
        MONEY {
            @Override
            public HSSFCell createCell(ExcelCellData data) {
                try {
                    HSSFCell cell = data.row.createCell(data.config.order());
                    Object value = data.field.get(data.item);
                    Optional<String> cellValue = Optional.empty();
                    if (value != null) {
                        FieldFormatStrategy strategy = FieldFormatResolver.getStrategy(value.getClass());
                        cellValue = strategy.format(value, Optional.empty());
                    }
                    cell.setCellValue(cellValue.orElse(""));
                    return cell;
                } catch (Exception e) {
                    LOG.error("Error to create cell money type.", e);
                    throw new RuntimeException(e);
                }
            }
        };

        public abstract HSSFCell createCell(ExcelCellData data);

        private static final Logger LOG = LoggerFactory.getLogger(CellType.class);
    }



}
