package com.example.employeemanagement.scheduler;

import com.example.employeemanagement.service.EmployeeCronJobService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.DisallowConcurrentExecution;
import org.springframework.stereotype.Component;

@Component
@DisallowConcurrentExecution
@Slf4j
public class EmployeeCronJob implements Job {

    private final EmployeeCronJobService service;

    public EmployeeCronJob(EmployeeCronJobService service) {
        this.service = service;
    }

    @Override
    public void execute(JobExecutionContext context)
            throws JobExecutionException {

        log.info("Employee Cron Job started");

        try {
            service.processPendingTasks();
            log.info("Employee Cron Job completed");
        } catch (Exception e) {
            log.error("Employee Cron Job failed", e);
            throw new JobExecutionException(e);
        }
    }
}