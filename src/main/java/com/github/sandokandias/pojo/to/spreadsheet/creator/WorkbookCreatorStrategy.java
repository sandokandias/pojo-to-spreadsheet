package com.github.sandokandias.pojo.to.spreadsheet.creator;

import com.github.sandokandias.pojo.to.spreadsheet.WorkbookData;

public interface WorkbookCreatorStrategy {

    WorkbookData create();

    boolean satisfied();

    void next(WorkbookCreatorStrategy next);

}
