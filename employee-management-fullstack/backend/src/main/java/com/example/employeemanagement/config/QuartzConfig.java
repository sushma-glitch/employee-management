package com.example.employeemanagement.config;

import com.example.employeemanagement.scheduler.EmployeeCronJob;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.JobBuilder;
import org.quartz.TriggerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;

@Configuration
public class QuartzConfig {

    @Value("${job.employee-processing}")
    private String cronExpression;

    @Bean
    public JobDetail employeeJobDetail() {
        return JobBuilder.newJob(EmployeeCronJob.class)
                .withIdentity("employeeCronJob")
                .storeDurably()
                .build();
    }

    @Bean
    public Trigger employeeJobTrigger(JobDetail employeeJobDetail) {
        return TriggerBuilder.newTrigger()
                .forJob(employeeJobDetail)
                .withIdentity("employeeCronTrigger")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule(cronExpression)
                )
                .build();
    }

    @Bean
    public SchedulerFactoryBeanCustomizer jobFactoryCustomizer(
            AutowireCapableBeanFactory beanFactory) {

        return factory -> {
            SpringBeanJobFactory jobFactory = new SpringBeanJobFactory() {
                @Override
                protected Object createJobInstance(
                        org.quartz.spi.TriggerFiredBundle bundle)
                        throws Exception {

                    Object job = super.createJobInstance(bundle);
                    beanFactory.autowireBean(job);
                    return job;
                }
            };

            factory.setJobFactory(jobFactory);
        };
    }
}