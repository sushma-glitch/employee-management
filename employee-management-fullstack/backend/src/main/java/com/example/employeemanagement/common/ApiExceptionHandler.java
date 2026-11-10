
package com.example.employeemanagement.common;

import com.example.employeemanagement.employee.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApiExceptionHandler {

    private static final Logger log =
            LoggerFactory.getLogger(ApiExceptionHandler.class);

    @ExceptionHandler(EmployeeNotFoundException.class)
    public ProblemDetail notFound(EmployeeNotFoundException e) {
        log.error("Employee not found: {}", e.getMessage());
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(DuplicateEmployeeEmailException.class)
    public ProblemDetail duplicate(DuplicateEmployeeEmailException e) {
        log.error("Duplicate employee email: {}", e.getMessage());
        return ProblemDetail.forStatusAndDetail(
                HttpStatus.CONFLICT, e.getMessage());
    }

    @ExceptionHandler({
        MethodArgumentNotValidException.class,
        DataIntegrityViolationException.class
})
public ProblemDetail badRequest(Exception e) {

    log.error("Employee API request failed: {}", e.getMessage(), e);

    System.err.println(">>> BAD REQUEST HANDLER HIT <<<");

    return ProblemDetail.forStatusAndDetail(
            HttpStatus.BAD_REQUEST,
            "Invalid request or duplicate employee email."
    );
}
}
