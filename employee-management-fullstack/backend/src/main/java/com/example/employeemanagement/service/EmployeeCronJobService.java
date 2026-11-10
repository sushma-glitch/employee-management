package com.example.employeemanagement.service;

import com.example.employeemanagement.task.EmployeeProcessingTask;
import com.example.employeemanagement.task.EmployeeProcessingTaskRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
@Slf4j
public class EmployeeCronJobService {

    private final EmployeeProcessingTaskRepository repository;

    public EmployeeCronJobService(
            EmployeeProcessingTaskRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public void processPendingTasks() {
        long lastId = 0L;

        while (true) {
            Slice<EmployeeProcessingTask> tasks =
                    repository.findByStatusAndIdGreaterThanOrderByIdAsc(
                            "PENDING",
                            lastId,
                            PageRequest.of(0, 100)
                    );

            if (tasks.isEmpty()) {
                log.info("No pending employee tasks found");
                return;
            }

            for (EmployeeProcessingTask task : tasks.getContent()) {
                lastId = task.getId();

                task.setStatus("IN_PROCESS");
                task.setLastModifiedDate(Instant.now());
                repository.saveAndFlush(task);

                log.info("Processing task ID: {}", task.getId());

                // Add actual business processing here.

                task.setStatus("COMPLETED");
                task.setLastModifiedDate(Instant.now());
                repository.save(task);

                log.info("Task completed: {}", task.getId());
            }

            if (!tasks.hasNext()) {
                break;
            }
        }
    }
}