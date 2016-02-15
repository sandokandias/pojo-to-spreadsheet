package com.github.sandokandias.pojo.to.spreadsheet;

import java.lang.reflect.Field;

import org.apache.poi.hssf.usermodel.HSSFRow;

public class ExcelCellData {

    public final ExcelCell config;
    public final Field field;
    public final HSSFRow row;
    public final Object item;

    public ExcelCellData(ExcelCell config, Field field, HSSFRow row, Object item) {
        this.config = config;
        this.field = field;
        this.row = row;
        this.item = item;
    }
}
