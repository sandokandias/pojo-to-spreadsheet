package com.github.sandokandias.pojo.to.spreadsheet.creator;

import java.io.File;

import com.github.sandokandias.pojo.to.spreadsheet.WorkbookData;

public final class FileNotExistsWorkbookCreator implements WorkbookCreatorStrategy {

    private WorkbookCreatorStrategy next;

    private File xlsFile;

    private String templatePath;

    public FileNotExistsWorkbookCreator(String xlsPath, String templatePath) {
        this.templatePath = templatePath;
        this.xlsFile = new File(xlsPath);
    }

    @Override
    public WorkbookData create() {
        if (!satisfied()) {
            return next.create();
        }
        return new WorkbookData(templatePath, xlsFile.getAbsolutePath());
    }

    @Override
    public boolean satisfied() {
        return !xlsFile.exists();
    }

    @Override
    public void next(WorkbookCreatorStrategy next) {
        this.next = next;
    }

}
