package com.example.employeemanagement.task;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeProcessingTaskRepository
        extends JpaRepository<EmployeeProcessingTask, Long> {

    Slice<EmployeeProcessingTask>
    findByStatusAndIdGreaterThanOrderByIdAsc(
            String status,
            Long id,
            Pageable pageable
    );
}