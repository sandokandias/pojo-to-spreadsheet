package com.github.sandokandias.pojo.to.spreadsheet;

import java.util.List;

import org.junit.Test;

import com.github.sandokandias.pojo.to.spreadsheet.setter.RowSetter;

public class CustomExcelPoiWriterTest extends AbstractExcelTest {

    @Override
    protected RowSetter<Employee> rowSetter() {
        return new EmployeeExcelRowSetter();
    }

    @Test
    public void ifWorkbookNotExceedingRowsShouldCreateOneFile() {
        List<Employee> employees = Employee.mockEmployees(100);
        excelPoiWriter.write(employees);

        assertSingleFile();
    }

    @Test
    public void ifWorkbookToExceedRowsShouldCreateOneFilePerWorkbook() {
        List<Employee> employees = Employee.mockEmployees(131000);
        int numOfFiles = (employees.size() / excelType.maxRows) + 1;
        excelPoiWriter.write(employees);

        assertMultiPartFiles(numOfFiles);
    }

}
