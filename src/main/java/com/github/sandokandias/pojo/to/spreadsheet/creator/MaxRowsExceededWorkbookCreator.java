package com.github.sandokandias.pojo.to.spreadsheet.creator;

import com.github.sandokandias.pojo.to.spreadsheet.ExcelType;
import com.github.sandokandias.pojo.to.spreadsheet.WorkbookData;

public final class MaxRowsExceededWorkbookCreator implements WorkbookCreatorStrategy {

    private WorkbookCreatorStrategy next;

    private Integer rowIndex;
    private ExcelType excelType;
    private String templatePath;
    private String fileName;

    public MaxRowsExceededWorkbookCreator(Integer rowIndex, ExcelType excelType, String templatePath, String fileName,
            Integer filePart) {
        this.rowIndex = rowIndex;
        this.excelType = excelType;
        this.fileName = ExcelPartNameResolver.resolve(excelType, fileName, filePart);
        this.templatePath = templatePath;
    }

    @Override
    public WorkbookData create() {
        if (!satisfied()) {
            return next.create();
        }
        return new WorkbookData(templatePath, fileName);
    }

    @Override
    public boolean satisfied() {
        return excelType.maxRows.intValue() == rowIndex.intValue();
    }

    @Override
    public void next(WorkbookCreatorStrategy next) {
        this.next = next;
    }
}
