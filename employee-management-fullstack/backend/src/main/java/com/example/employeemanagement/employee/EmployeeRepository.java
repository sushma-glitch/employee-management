package com.example.employeemanagement.employee;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmailIgnoreCase(String email);

    List<Employee> findByDepartmentIgnoreCase(String department);
}
