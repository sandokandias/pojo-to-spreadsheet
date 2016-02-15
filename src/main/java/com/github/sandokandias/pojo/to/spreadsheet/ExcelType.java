package com.github.sandokandias.pojo.to.spreadsheet;

public enum ExcelType {
    XLS(65000),
    XLSX(1000000);

    public final Integer maxRows;

    private ExcelType(Integer maxRows) {
        this.maxRows = maxRows;
    }

    public String extension() {
        return this.name().toLowerCase();
    }
}
