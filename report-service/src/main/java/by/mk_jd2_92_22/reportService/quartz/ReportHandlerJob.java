package by.mk_jd2_92_22.reportService.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportHandlerJob implements Job {

    @Autowired
    private JobService jobService;



    @Override
    public void execute(JobExecutionContext jobExecutionContext){

        this.jobService.handleReport();

    }
}
