package com.github.sandokandias.pojo.to.spreadsheet;

import java.io.File;
import java.io.FileInputStream;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkbookData {

    private static final Logger LOG = LoggerFactory.getLogger(WorkbookData.class);

    private HSSFWorkbook workbook;
    private HSSFSheet sheet;
    private String fileName;

    public WorkbookData(String templatePath, String fileName) {
        buildFrom(templatePath, fileName);

    }

    public WorkbookData(File file) {
        buildFrom(file);
    }

    private void buildFrom(File file) {
        try {
            this.workbook = new HSSFWorkbook(new FileInputStream(file));
            this.sheet = workbook.getSheetAt(0);
            this.fileName = file.getName();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void buildFrom(String templatePath, String fileName) {
        try {
            File templateFile = new File(templatePath);
            LOG.debug("Template file {}", templateFile.getAbsolutePath());
            LOG.debug("Excel file {}", fileName);
            if (templateFile.exists()) {
                buildFrom(templateFile);
            } else {
                this.workbook = new HSSFWorkbook();
                this.sheet = workbook.createSheet();
            }
            this.fileName = fileName;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public HSSFWorkbook getWorkbook() {
        return workbook;
    }

    public HSSFSheet getSheet() {
        return sheet;
    }

    public String getFileName() {
        return fileName;
    }
}
