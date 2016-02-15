package com.github.sandokandias.pojo.to.spreadsheet.setter;

import org.apache.poi.hssf.usermodel.HSSFRow;

public interface RowSetter<T> {

    void setValues(T item, HSSFRow row, boolean isHeader) throws Exception;

}
