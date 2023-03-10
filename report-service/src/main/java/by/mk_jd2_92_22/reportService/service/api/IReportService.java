package by.mk_jd2_92_22.reportService.service.api;

import by.mk_jd2_92_22.reportService.service.dto.PageDTO;
import by.mk_jd2_92_22.reportService.service.dto.ReportParams;
import by.mk_jd2_92_22.reportService.model.Report;
import by.mk_jd2_92_22.reportService.model.ReportType;
import by.mk_jd2_92_22.reportService.model.Status;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.util.UUID;

public interface IReportService {
    void create(ReportParams params, ReportType type, UUID profile, HttpHeaders token);
    PageDTO<Report> get(int page, int size, UUID profileId, HttpHeaders token);
    void validation(UUID reportUuid, HttpHeaders token);
    byte[] export(UUID reportUuid, HttpHeaders token);
    void updateStatus(UUID reportId, LocalDateTime dtUpdate, Status status);
    Report getLoaded();
    Report get(UUID reportId);
}
