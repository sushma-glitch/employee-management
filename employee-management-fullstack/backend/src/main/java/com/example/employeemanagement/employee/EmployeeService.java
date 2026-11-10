//
//package com.example.employeemanagement.employee;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@Service
//@Transactional
//public class EmployeeService {
//
//    private static final Logger log =
//            LoggerFactory.getLogger(EmployeeService.class);
//
//    private final EmployeeRepository repository;
//
//    public EmployeeService(EmployeeRepository repository) {
//        this.repository = repository;
//    }
//
//    @Transactional(readOnly = true)
//    public List<Employee> findAll() {
//        log.trace("Entering findAll method");
//        log.info("Fetching all employees");
//
//        List<Employee> employees = repository.findAll();
//
//        log.info("Successfully fetched {} employees", employees.size());
//        log.trace("Exiting findAll method");
//        return employees;
//    }
//
//    @Cacheable(cacheNames = "employees", key = "#id")
//    @Transactional(readOnly = true)
//    public Employee findById(Long id) {
//        log.trace("Entering findById method with ID: {}", id);
//        log.info("Fetching employee with ID: {}", id);
//
//        Employee employee = repository.findById(id)
//                .orElseThrow(() -> {
//                    log.warn("Employee not found with ID: {}", id);
//                    return new EmployeeNotFoundException(id);
//                });
//
//        log.info("Employee details fetched successfully for ID: {}", id);
//        log.trace("Exiting findById method");
//        return employee;
//    }
//
//    @Transactional(readOnly = true)
//    public List<Employee> findByDepartment(String department) {
//        log.trace("Entering findByDepartment: {}", department);
//        log.debug("Searching employees by department: {}", department);
//
//        List<Employee> employees =
//                repository.findByDepartmentIgnoreCase(department);
//
//        if (employees.isEmpty()) {
//            log.warn("No employees found for department: {}", department);
//        } else {
//            log.info("Found {} employees in department: {}",
//                    employees.size(), department);
//        }
//
//        log.trace("Exiting findByDepartment method");
//        return employees;
//    }
//
//    @CacheEvict(cacheNames = "employees", allEntries = true)
//    public Employee create(Employee e) {
//        log.trace("Entering create method");
//        log.info("Creating employee with email: {}", e.getEmail());
//
//        if (repository.findByEmailIgnoreCase(e.getEmail()).isPresent()) {
//            log.warn("Duplicate employee email: {}", e.getEmail());
//            throw new DuplicateEmployeeEmailException(e.getEmail());
//        }
//
//        Employee savedEmployee = repository.save(e);
//        log.info("Employee created successfully with ID: {}", savedEmployee.getId());
//        log.trace("Exiting create method");
//        return savedEmployee;
//    }
//
//    @CacheEvict(cacheNames = "employees", allEntries = true)
//    public Employee update(Long id, Employee e) {
//        log.trace("Entering update method for ID: {}", id);
//        log.info("Updating employee with ID: {}", id);
//
//        Employee old = repository.findById(id).orElseThrow(() -> {
//            log.warn("Employee update failed: ID {} not found", id);
//            return new EmployeeNotFoundException(id);
//        });
//
//        repository.findByEmailIgnoreCase(e.getEmail()).ifPresent(found -> {
//            if (!found.getId().equals(id)) {
//                log.warn("Duplicate email during update: {}", e.getEmail());
//                throw new DuplicateEmployeeEmailException(e.getEmail());
//            }
//        });
//
//        old.setFirstName(e.getFirstName());
//        old.setLastName(e.getLastName());
//        old.setEmail(e.getEmail());
//        old.setDepartment(e.getDepartment());
//        old.setSalary(e.getSalary());
//
//        Employee updatedEmployee = repository.save(old);
//        log.info("Employee updated successfully with ID: {}", id);
//        log.trace("Exiting update method");
//        return updatedEmployee;
//    }
//
//    @CacheEvict(cacheNames = "employees", allEntries = true)
//    public void delete(Long id) {
//        log.trace("Entering delete method for ID: {}", id);
//        log.info("Deleting employee with ID: {}", id);
//
//        if (!repository.existsById(id)) {
//            log.warn("Employee deletion failed: ID {} not found", id);
//            throw new EmployeeNotFoundException(id);
//        }
//
//        repository.deleteById(id);
//        log.info("Employee deleted successfully with ID: {}", id);
//        log.trace("Exiting delete method");
//    }
//}




package com.example.employeemanagement.employee;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@Transactional
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Employee> findAll() {
        log.trace("Entering findAll method");
        log.info("Fetching all employees");

        List<Employee> employees = repository.findAll();

        log.info("Successfully fetched {} employees", employees.size());
        log.trace("Exiting findAll method");

        return employees;
    }

    @Cacheable(cacheNames = "employees", key = "#id")
    @Transactional(readOnly = true)
    public Employee findById(Long id) {
        log.trace("Entering findById method with ID: {}", id);
        log.info("Fetching employee with ID: {}", id);

        Employee employee = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee not found with ID: {}", id);
                    return new EmployeeNotFoundException(id);
                });

        log.info("Employee details fetched successfully for ID: {}", id);
        log.trace("Exiting findById method");

        return employee;
    }

    @Transactional(readOnly = true)
    public List<Employee> findByDepartment(String department) {
        log.trace("Entering findByDepartment: {}", department);
        log.debug("Searching employees by department: {}", department);

        List<Employee> employees =
                repository.findByDepartmentIgnoreCase(department);

        if (employees.isEmpty()) {
            log.warn("No employees found for department: {}", department);
        } else {
            log.info("Found {} employees in department: {}",
                    employees.size(), department);
        }

        log.trace("Exiting findByDepartment method");
        return employees;
    }

    @CacheEvict(cacheNames = "employees", allEntries = true)
    public Employee create(Employee e) {
        log.trace("Entering create method");
        log.info("Creating employee with email: {}", e.getEmail());

        if (repository.findByEmailIgnoreCase(e.getEmail()).isPresent()) {
            log.warn("Duplicate employee email: {}", e.getEmail());
            throw new DuplicateEmployeeEmailException(e.getEmail());
        }

        Employee savedEmployee = repository.save(e);

        log.info("Employee created successfully with ID: {}",
                savedEmployee.getId());
        log.trace("Exiting create method");

        return savedEmployee;
    }

    @CacheEvict(cacheNames = "employees", allEntries = true)
    public Employee update(Long id, Employee e) {
        log.trace("Entering update method for ID: {}", id);
        log.info("Updating employee with ID: {}", id);

        Employee old = repository.findById(id)
                .orElseThrow(() -> {
                    log.warn("Employee update failed: ID {} not found", id);
                    return new EmployeeNotFoundException(id);
                });

        repository.findByEmailIgnoreCase(e.getEmail()).ifPresent(found -> {
            if (!found.getId().equals(id)) {
                log.warn("Duplicate email during update: {}", e.getEmail());
                throw new DuplicateEmployeeEmailException(e.getEmail());
            }
        });

        old.setFirstName(e.getFirstName());
        old.setLastName(e.getLastName());
        old.setEmail(e.getEmail());
        old.setDepartment(e.getDepartment());
        old.setSalary(e.getSalary());

        Employee updatedEmployee = repository.save(old);

        log.info("Employee updated successfully with ID: {}", id);
        log.trace("Exiting update method");

        return updatedEmployee;
    }

    @CacheEvict(cacheNames = "employees", allEntries = true)
    public void delete(Long id) {
        log.trace("Entering delete method for ID: {}", id);
        log.info("Deleting employee with ID: {}", id);

        if (!repository.existsById(id)) {
            log.warn("Employee deletion failed: ID {} not found", id);
            throw new EmployeeNotFoundException(id);
        }

        repository.deleteById(id);

        log.info("Employee deleted successfully with ID: {}", id);
        log.trace("Exiting delete method");
    }
}
