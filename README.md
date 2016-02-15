# pojo-to-spreadsheet

An Java api to export pojos in spreadsheets

[![Build Status](https://secure.travis-ci.org/sandokandias/pojo-to-spreadsheet.png?branch=master)](http://travis-ci.org/sandokandias/pojo-to-spreadsheet?branch=master)
[![Test Coverage](http://codecov.io/github/sandokandias/pojo-to-spreadsheet/coverage.svg?branch=master)](http://codecov.io/github/sandokandias/pojo-to-spreadsheet?branch=master)
[![License](http://img.shields.io/badge/license-CC0%201.0-blue.svg)](https://creativecommons.org/publicdomain/zero/1.0/legalcode)


## Status

This project is currently under development. Feel free to implement new formats or suggest improvements / corrections via pull request, as well as any correction of English used incorrectly.
\0/

## Features

* Export via Annotation
* Export via your own RowSetter
* Formats supported in XLS and XLSX

## <a name="quick-start">Quick Start</a>


###via Annotation

```Java
 // Pojo

public class Employee implements Serializable {

    private static final long serialVersionUID = 3746741846449261791L;

    @ExcelCell(order = 0, label = "Name")
    public final String name;

    @ExcelCell(order = 1, label = "CPF", formatPattern = "###.###.###-##")
    public final String cpf;

    @ExcelCell(order = 2, label = "Email")
    public final String email;

    @ExcelCell(type = CellType.DATE, order = 3, label = "Birthdate", formatPattern = "dd/MM/yyyy")
    public final Date birthDate;

    @ExcelCell(type = CellType.NUMBER, order = 4, label = "Age")
    public final Integer age;

    @ExcelCell(type = CellType.MONEY, order = 5, label = "Salary")
    public final BigDecimal salary;

    @ExcelCell(type = CellType.DATE, order = 6, label = "Admission at", formatPattern = "EEE, MMM d, ''yyyy")
    public final LocalDate registerDate;

    @ExcelCell(type = CellType.DATE, order = 7, label = "Resignation at", formatPattern = "dd/MM/yyyy HH:mm:ss")
    public final LocalDateTime resignationDate;

    // constructor omitted
}


// Writer
ExcelPoiWriter<Employee> writer = new ExcelPoiWriterBuilder<>()
	.writerMode(ExcelWriterMode.IN_FILE)
	.excelType(ExcelType.XLS)
	.excelPath("./employees.xls")
	.rowSetter(new AnnotationRowSetter<Employee>())
	.build();

List<Employee> employees = repository.getEmployees();

ExcelWriterResult result = writer.write(employees);

/* If you need the name of the files to build a download response for example, you can get it from the result
 *  It's a list, because it can break in multi files when your excel file exceed the limit of rows
 */
List<String> filesName =result.getFilesName();
```

###via your own RowSetter

```Java
 // coming soon
```

## Backlog

* Support more formats
