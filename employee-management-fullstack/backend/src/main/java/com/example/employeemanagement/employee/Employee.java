package com.example.employeemanagement.employee;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "employees", uniqueConstraints = @UniqueConstraint(name = "uk_employee_email", columnNames = "email"))
public class Employee implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(nullable = false, length = 100)
    private String firstName;
    @NotBlank
    @Column(nullable = false, length = 100)
    private String lastName;
    @NotBlank
    @Email
    @Column(nullable = false, unique = true, length = 180)
    private String email;
    @NotBlank
    @Column(nullable = false, length = 100)
    private String department;
    @NotNull
    @PositiveOrZero
    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal salary;

    protected Employee() {
    }

    public Employee(String firstName, String lastName, String email, String department, BigDecimal salary) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.department = department;
        this.salary = salary;
    }

    public Long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getDepartment() {
        return department;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setId(Long v) {
        id = v;
    }

    public void setFirstName(String v) {
        firstName = v;
    }

    public void setLastName(String v) {
        lastName = v;
    }

    public void setEmail(String v) {
        email = v;
    }

    public void setDepartment(String v) {
        department = v;
    }

    public void setSalary(BigDecimal v) {
        salary = v;
    }
}
