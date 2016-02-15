package com.github.sandokandias.pojo.to.spreadsheet.setter;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.github.sandokandias.pojo.to.spreadsheet.ExcelCell;
import com.github.sandokandias.pojo.to.spreadsheet.ExcelCellData;


public class AnnotationRowSetter<T extends Serializable> implements RowSetter<T> {

    @Override
    public void setValues(T item, HSSFRow row, boolean isHeader) throws Exception {
        Field[] fields = item.getClass().getDeclaredFields();
        if (isHeader) {
            createHeaders(fields, row);
        } else {
            createCells(fields, row, item);
        }
    }

    private void createCells(Field[] fields, HSSFRow row, T item) {
        Arrays.stream(fields).forEach(f -> createCell(f, item, row));
    }

    private void createCell(Field field, T item, HSSFRow row) {
        field.setAccessible(true);
        ExcelCell excelCell = getExcelCellFrom(field);
        if (excelCell != null) {
            ExcelCellData cellData = new ExcelCellData(excelCell, field, row, item);
            excelCell.type().createCell(cellData);
        }
    }

    private ExcelCell getExcelCellFrom(Field field) {
        try {
            return (ExcelCell) Arrays.stream(field.getDeclaredAnnotations())
                    .filter(a -> a.annotationType().isAssignableFrom(ExcelCell.class)).findFirst().get();
        } catch (Exception e) {
            return null;
        }
    }

    private void createHeaders(Field[] fields, HSSFRow row) {
        Arrays.stream(fields).forEach(f -> createHeader(f, row));
    }

    private void createHeader(Field field, HSSFRow row) {
        ExcelCell excelCell = getExcelCellFrom(field);
        if (excelCell != null) {
            HSSFCell cell = row.createCell(excelCell.order());
            cell.setCellValue(excelCell.label());
        }
    }

}
