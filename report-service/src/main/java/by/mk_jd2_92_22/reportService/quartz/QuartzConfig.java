package by.mk_jd2_92_22.reportService.quartz;

import org.quartz.*;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

import java.io.IOException;
import java.util.Properties;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;

@Configuration
public class QuartzConfig {

    private final ApplicationContext applicationContext;

    public QuartzConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public SpringBeanJobFactory springBeanJobFactory() {
        AutoWiringSpringBeanJobFactory jobFactory = new AutoWiringSpringBeanJobFactory();

        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        factory.setJobFactory(springBeanJobFactory());
        factory.setQuartzProperties(quartzProperties());
        return factory;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("/quartz.properties"));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }


    @Bean
    public JobDetail jobDetail() {
        return JobBuilder.newJob().ofType(ReportHandlerJob.class)
                .storeDurably()
                .withIdentity("Qrtz_Job_Detail")
                .withDescription("Invoke Job service...")
                .build();
    }

    @Bean
    public Trigger trigger(JobDetail job) {
        return TriggerBuilder.newTrigger().forJob(job)
                .withIdentity("Qrtz_Trigger")
                .withDescription("trigger")
                .withSchedule(simpleSchedule().repeatForever().withIntervalInSeconds(30))
                .build();
    }

    @Bean
    public Scheduler scheduler(Trigger trigger, JobDetail job, SchedulerFactoryBean factory)
            throws SchedulerException {
        Scheduler scheduler = factory.getScheduler();
        scheduler.scheduleJob(job, trigger);
        scheduler.start();
        return scheduler;
    }
}
