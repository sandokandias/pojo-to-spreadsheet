package com.github.sandokandias.pojo.to.spreadsheet;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

import com.github.sandokandias.pojo.to.spreadsheet.setter.RowSetter;
import com.github.sandokandias.pojo.to.spreadsheet.writer.ExcelPoiWriter;
import com.github.sandokandias.pojo.to.spreadsheet.writer.ExcelPoiWriter.ExcelPoiWriterBuilder;
import com.github.sandokandias.pojo.to.spreadsheet.writer.ExcelWriterMode;


public abstract class AbstractExcelTest {

    private static final String EXCEL_PATH = "./users_report.xls";

    protected ExcelPoiWriter<Employee> excelPoiWriter;
    protected ExcelType excelType;

    protected List<File> filesToRemove;

    protected abstract RowSetter<Employee> rowSetter();

    @Before
    public void beforeRun() {
        this.excelType = ExcelType.XLS;
        this.excelPoiWriter = new ExcelPoiWriterBuilder<Employee>().writerMode(ExcelWriterMode.IN_FILE)
                .excelType(excelType).excelPath(EXCEL_PATH).rowSetter(rowSetter()).build();
        this.filesToRemove = new ArrayList<>();
    }

    @After
    public void afterRun() {
        filesToRemove.forEach(file -> file.delete());
    }

    protected void assertSingleFile() {
        List<String> filesName = excelPoiWriter.getResult().getFilesName();
        String fileName = filesName.stream().findFirst().get();
        File createdFile = new File(fileName);

        filesToRemove.add(createdFile);
        Assert.assertTrue(createdFile.exists());
    }

    protected void assertMultiPartFiles(int numOfFiles) {
        List<String> filesName = excelPoiWriter.getResult().getFilesName();
        Stream<File> createdFiles = filesName.stream().map(fn -> new File(fn));

        filesToRemove.addAll(createdFiles.collect(Collectors.toList()));
        Assert.assertEquals(numOfFiles, filesName.size());
    }

}
