package com.github.sandokandias.pojo.to.spreadsheet.writer;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public enum ExcelWriterMode {

    IN_FILE {
        @Override
        public ExcelWriterResult write(HSSFWorkbook workbook, String fileName) {

            try (FileOutputStream stream = new FileOutputStream(fileName);) {
                workbook.write(stream);
                return new ExcelWriterResult(fileName);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    workbook.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    };

    public abstract ExcelWriterResult write(HSSFWorkbook workbook, String fileName);
}
