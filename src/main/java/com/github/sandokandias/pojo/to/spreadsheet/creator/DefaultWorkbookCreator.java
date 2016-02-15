package com.github.sandokandias.pojo.to.spreadsheet.creator;

import com.github.sandokandias.pojo.to.spreadsheet.WorkbookData;

public final class DefaultWorkbookCreator implements WorkbookCreatorStrategy {

    private String fileName;

    public DefaultWorkbookCreator(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public WorkbookData create() {
        return new WorkbookData("", fileName);
    }

    @Override
    public boolean satisfied() {
        return true;
    }

    @Override
    public void next(WorkbookCreatorStrategy next) {
        throw new UnsupportedOperationException("Next strategy not found.");
    }

}
