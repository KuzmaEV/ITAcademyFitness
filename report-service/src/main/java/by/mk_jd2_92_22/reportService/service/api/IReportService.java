package by.mk_jd2_92_22.reportService.service.api;

import by.mk_jd2_92_22.reportService.dto.PageDTO;
import by.mk_jd2_92_22.reportService.dto.ReportParams;
import by.mk_jd2_92_22.reportService.model.Report;
import by.mk_jd2_92_22.reportService.model.ReportType;
import org.springframework.http.HttpHeaders;

public interface IReportService {
    void create(ReportParams params, ReportType type, HttpHeaders token);
    PageDTO<Report> get(int page, int size);
}
