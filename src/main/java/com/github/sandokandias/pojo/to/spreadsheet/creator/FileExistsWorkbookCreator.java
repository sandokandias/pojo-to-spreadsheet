package com.github.sandokandias.pojo.to.spreadsheet.creator;

import java.io.File;

import com.github.sandokandias.pojo.to.spreadsheet.WorkbookData;

public final class FileExistsWorkbookCreator implements WorkbookCreatorStrategy {

    private WorkbookCreatorStrategy next;

    private File xlsFile;

    public FileExistsWorkbookCreator(String xlsPath) {
        this.xlsFile = new File(xlsPath);
    }

    @Override
    public WorkbookData create() {
        if (!satisfied()) {
            return next.create();
        }
        return new WorkbookData(xlsFile);
    }

    @Override
    public boolean satisfied() {
        return xlsFile.exists();
    }

    @Override
    public void next(WorkbookCreatorStrategy next) {
        this.next = next;
    }

}
