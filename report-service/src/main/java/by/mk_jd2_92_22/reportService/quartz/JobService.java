package by.mk_jd2_92_22.reportService.quartz;

import by.mk_jd2_92_22.reportService.minio.MinioService;
import by.mk_jd2_92_22.reportService.minio.ReportHandler;
import by.mk_jd2_92_22.reportService.model.Report;
import by.mk_jd2_92_22.reportService.model.Status;
import by.mk_jd2_92_22.reportService.service.api.IReportService;
import org.springframework.stereotype.Component;

@Component
public class JobService {

    private final IReportService reportService;
    private final ReportHandler reportHandler;
    private final MinioService minioComponent;

    public JobService(IReportService reportService, ReportHandler reportHandler, MinioService minioComponent) {
        this.reportService = reportService;
        this.reportHandler = reportHandler;
        this.minioComponent = minioComponent;
    }

    public void handleReport(){

        Report loaded = this.reportService.getLoaded();
        if (loaded == null){
            return;
        }
        this.reportService.updateStatus(loaded.getUuid(), loaded.getDtUpdate(), Status.PROGRESS);
        Report progress = this.reportService.get(loaded.getUuid());

        byte[] report;
        try {
            report = this.reportHandler.generateReport(progress);
            this.minioComponent.put(progress.getUuid().toString(), report);
        } catch (Exception e) {
            e.printStackTrace();
            this.reportService.updateStatus(progress.getUuid(), progress.getDtUpdate(), Status.ERROR);
            throw new IllegalArgumentException("Не удалось обработать отчет: " + e.getMessage());
        }

        this.reportService.updateStatus(progress.getUuid(), progress.getDtUpdate(), Status.DONE);


    }
}
