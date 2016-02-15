package com.github.sandokandias.pojo.to.spreadsheet.writer;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.sandokandias.pojo.to.spreadsheet.ExcelType;
import com.github.sandokandias.pojo.to.spreadsheet.WorkbookData;
import com.github.sandokandias.pojo.to.spreadsheet.creator.DefaultWorkbookCreator;
import com.github.sandokandias.pojo.to.spreadsheet.creator.FileExistsWorkbookCreator;
import com.github.sandokandias.pojo.to.spreadsheet.creator.FileNotExistsWorkbookCreator;
import com.github.sandokandias.pojo.to.spreadsheet.creator.MaxRowsExceededWorkbookCreator;
import com.github.sandokandias.pojo.to.spreadsheet.creator.WorkbookCreatorStrategy;
import com.github.sandokandias.pojo.to.spreadsheet.setter.RowSetter;

public class ExcelPoiWriter<T extends Serializable> {

    private static final Logger LOG = LoggerFactory.getLogger(ExcelPoiWriter.class);

    private ExcelType excelType;
    private ExcelWriterMode writerMode;
    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private RowSetter<T> rowSetter;
    private ExcelWriterResult writerResult;
    private String currentPath;
    private String originalPath;
    private String templatePath;
    private Integer skipRows;
    private Integer filePart = 0;
    private Integer rowIndex;
    private boolean hasHeader = false;

    public ExcelPoiWriter(ExcelPoiWriterBuilder<T> builder) {
        this.excelType = builder.excelType;
        this.writerMode = builder.writerMode;
        this.originalPath = builder.excelPath;
        this.rowSetter = builder.rowSetter;
        this.skipRows = builder.skipRows;
        this.templatePath = StringUtils.isNotBlank(builder.templatePath) ? builder.templatePath : StringUtils.EMPTY;
        this.writerResult = new ExcelWriterResult();
    }

    public ExcelWriterResult getResult() {
        return writerResult;
    }

    private void close() {
        try {
            ExcelWriterResult result = writerMode.write(workbook, currentPath);
            writerResult.compose(result);
        } catch (Exception e) {
            LOG.error("Error to close workbook.", e);
            throw new RuntimeException(e);
        }
    }

    private void open(int rowIndex) {
        WorkbookCreatorStrategy defaultCreator = new DefaultWorkbookCreator(originalPath);

        WorkbookCreatorStrategy fileNotExistsCreator = new FileNotExistsWorkbookCreator(originalPath, templatePath);
        fileNotExistsCreator.next(defaultCreator);

        WorkbookCreatorStrategy fileExistsCreator = new FileExistsWorkbookCreator(originalPath);
        fileExistsCreator.next(fileNotExistsCreator);

        WorkbookCreatorStrategy maxRowsExceededCreator =
                new MaxRowsExceededWorkbookCreator(rowIndex, excelType, templatePath, originalPath, filePart);
        maxRowsExceededCreator.next(fileExistsCreator);

        WorkbookData wb = maxRowsExceededCreator.create();
        this.workbook = wb.getWorkbook();
        this.sheet = wb.getSheet();
        this.currentPath = wb.getFileName();
    }

    public void write(List<T> items) {
        try {
            if (items == null || items.isEmpty()) {
                LOG.warn("Items to write are empty.");
                return;
            }
            rowIndex = skipRows;
            open(rowIndex);
            items.forEach(item -> writeItem(item));
            close();
        } catch (Exception e) {
            LOG.error("Error to write workbook.", e);
            throw e;
        }
    }

    private void writeItem(T item) {
        try {
            checkHeader(item);
            checkWorkbookSize(item);
            createCell(item);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void createCell(T item) throws Exception {
        HSSFRow row = sheet.createRow(rowIndex);
        rowSetter.setValues(item, row, false);
        rowIndex++;
    }

    private void checkWorkbookSize(T item) throws Exception {
        if (maxRowsExceeded(rowIndex)) {
            this.filePart += 1;
            close();
            open(rowIndex);
            hasHeader = false;
            checkHeader(item);
        }
    }

    private void checkHeader(T item) throws Exception {
        if (!hasHeader && skipRows == 0) {
            HSSFRow row = sheet.createRow(0);
            rowSetter.setValues(item, row, true);
            hasHeader = true;
            rowIndex = 1;
        }
    }

    private boolean maxRowsExceeded(int rowIndex) {
        return rowIndex == excelType.maxRows;
    }

    public static class ExcelPoiWriterBuilder<T extends Serializable> {
        private RowSetter<T> rowSetter;
        private String excelPath;
        private String templatePath;
        private Integer skipRows = 0;
        private ExcelType excelType;
        private ExcelWriterMode writerMode;

        public ExcelPoiWriterBuilder<T> excelType(ExcelType xlsType) {
            this.excelType = xlsType;
            return this;
        }

        public ExcelPoiWriterBuilder<T> rowSetter(RowSetter<T> rowSetter) {
            this.rowSetter = rowSetter;
            return this;
        }

        public ExcelPoiWriterBuilder<T> excelPath(String excelPath) {
            this.excelPath = excelPath;
            return this;
        }

        public ExcelPoiWriterBuilder<T> templatePath(String templatePath) {
            this.templatePath = templatePath;
            return this;
        }

        public ExcelPoiWriterBuilder<T> skipRows(Integer skipRows) {
            if (skipRows > 0) {
                this.skipRows = skipRows;
            }
            return this;
        }

        public ExcelPoiWriterBuilder<T> writerMode(ExcelWriterMode writerMode) {
            this.writerMode = writerMode;
            return this;
        }

        public ExcelPoiWriter<T> build() {
            return new ExcelPoiWriter<T>(this);
        }

    }
}
