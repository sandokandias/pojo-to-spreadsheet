package com.github.sandokandias.pojo.to.spreadsheet;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.github.sandokandias.pojo.to.spreadsheet.ExcelCell.CellType;


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

    public Employee(String name, String cpf, String email, Date birthDate, Integer age, BigDecimal salary,
            LocalDate registerDate, LocalDateTime resignationDate) {
        this.name = name;
        this.cpf = cpf;
        this.email = email;
        this.birthDate = birthDate;
        this.age = age;
        this.salary = salary;
        this.registerDate = registerDate;
        this.resignationDate = resignationDate;
    }

    public static List<Employee> mockEmployees(int size) {
        return IntStream.range(1, size).mapToObj(i -> createEmployee(i)).collect(Collectors.toList());
    }

    private static Employee createEmployee(int i) {
        return new Employee("employee " + i, "01252460643", String.format("employee.%d@gmail.com", i), new Date(), i,
                new BigDecimal(i * 2), LocalDate.now(), LocalDateTime.now());
    }
}
