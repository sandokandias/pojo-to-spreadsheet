package com.github.sandokandias.pojo.to.spreadsheet.creator;

import com.github.sandokandias.pojo.to.spreadsheet.ExcelType;

public final class ExcelPartNameResolver {

    private static final String FILE_ORIGINAL_NAME = ".%s";
    private static final String FILE_PART_NAME = "_%s.%s";
    
    private ExcelPartNameResolver() {
    }

    public static String resolve(ExcelType excelType, String fileName, Integer filePart) {
        return fileName.replace(String.format(FILE_ORIGINAL_NAME, excelType.extension()),
                String.format(FILE_PART_NAME, filePart.toString(), excelType.extension()));
    }
}
