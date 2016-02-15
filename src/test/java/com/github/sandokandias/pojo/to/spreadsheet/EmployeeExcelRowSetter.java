package com.github.sandokandias.pojo.to.spreadsheet;

import java.util.Optional;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

import com.github.sandokandias.pojo.to.spreadsheet.format.FieldFormatStrategy;
import com.github.sandokandias.pojo.to.spreadsheet.format.LocalDateFormat;
import com.github.sandokandias.pojo.to.spreadsheet.format.LocalDateTimeFormat;
import com.github.sandokandias.pojo.to.spreadsheet.format.MoneyFormat;
import com.github.sandokandias.pojo.to.spreadsheet.format.TextFormat;
import com.github.sandokandias.pojo.to.spreadsheet.format.UtilDateFormat;
import com.github.sandokandias.pojo.to.spreadsheet.setter.RowSetter;

public class EmployeeExcelRowSetter implements RowSetter<Employee> {

    private FieldFormatStrategy utilDateFormat = new UtilDateFormat();
    private FieldFormatStrategy localDateFormat = new LocalDateFormat();
    private FieldFormatStrategy localDateTimeFormat = new LocalDateTimeFormat();
    private FieldFormatStrategy textFormat = new TextFormat();
    private FieldFormatStrategy moneyFormat = new MoneyFormat();

    @Override
    public void setValues(Employee item, HSSFRow row, boolean isHeader) throws Exception {
        HSSFCell cellLogin = row.createCell(0);
        cellLogin.setCellValue(item.name);

        HSSFCell cellCpf = row.createCell(1);
        Optional<String> cpf = textFormat.format(item.cpf, Optional.of("###.###.###-##"));
        cellCpf.setCellValue(cpf.orElse(""));

        HSSFCell cellPassword = row.createCell(1);
        cellPassword.setCellValue(item.email);

        HSSFCell cellBirthdate = row.createCell(2);
        Optional<String> birthDate = utilDateFormat.format(item.birthDate, Optional.of("dd/MM/yyyy"));
        cellBirthdate.setCellValue(birthDate.orElse(""));

        HSSFCell cellAge = row.createCell(3);
        cellAge.setCellValue(item.age);

        HSSFCell cellSalary = row.createCell(4);
        Optional<String> salary = moneyFormat.format(item.salary, Optional.empty());
        cellSalary.setCellValue(salary.orElse(""));

        HSSFCell cellRegisterDate = row.createCell(5);
        Optional<String> registerDate = localDateFormat.format(item.registerDate, Optional.of("dd/MM/yyyy"));
        cellRegisterDate.setCellValue(registerDate.orElse(""));

        HSSFCell cellResignationDate = row.createCell(6);
        Optional<String> resignationDate = localDateTimeFormat.format(item.resignationDate, Optional.of("dd/MM/yyyy"));
        cellResignationDate.setCellValue(resignationDate.orElse(""));
    }

}
