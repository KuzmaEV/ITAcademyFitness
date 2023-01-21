package by.mk_jd2_92_22.reportService.service.api;

import by.mk_jd2_92_22.reportService.model.Report;
import org.springframework.http.HttpHeaders;

public interface IResponseReportService {
    byte[] generateReport(Report report, HttpHeaders token);
}
