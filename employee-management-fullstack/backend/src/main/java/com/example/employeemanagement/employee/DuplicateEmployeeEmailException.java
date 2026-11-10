package com.example.employeemanagement.employee;

public class DuplicateEmployeeEmailException extends RuntimeException {
    public DuplicateEmployeeEmailException(String email) {
        super("An employee already exists with email: " + email);
    }
}
