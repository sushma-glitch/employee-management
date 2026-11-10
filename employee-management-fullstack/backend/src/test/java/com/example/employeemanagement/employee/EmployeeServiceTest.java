package com.example.employeemanagement.employee;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTest {
    @Mock
    EmployeeRepository repository;
    EmployeeService service;

    @BeforeEach
    void setUp() {
        service = new EmployeeService(repository);
    }

    @Test
    void findsEmployee() {
        Employee e = new Employee("Asha", "Sharma", "asha@example.com", "Engineering", new BigDecimal("85000"));
        e.setId(1L);
        when(repository.findById(1L)).thenReturn(Optional.of(e));
        assertEquals("asha@example.com", service.findById(1L).getEmail());
    }

    @Test
    void missingEmployeeThrows() {
        when(repository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> service.findById(99L));
    }

    @Test
    void duplicateEmailRejected() {
        Employee e = new Employee("Asha", "Sharma", "asha@example.com", "Engineering", new BigDecimal("85000"));
        when(repository.findByEmailIgnoreCase(e.getEmail())).thenReturn(Optional.of(e));
        assertThrows(DuplicateEmployeeEmailException.class, () -> service.create(e));
        verify(repository, never()).save(any());
    }
}
