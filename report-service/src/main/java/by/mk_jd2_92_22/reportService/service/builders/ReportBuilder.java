package by.mk_jd2_92_22.reportService.service.builders;

import by.mk_jd2_92_22.reportService.dto.ReportParams;
import by.mk_jd2_92_22.reportService.model.Report;
import by.mk_jd2_92_22.reportService.model.Status;
import by.mk_jd2_92_22.reportService.model.ReportType;

import java.time.LocalDateTime;
import java.util.UUID;

public class ReportBuilder {

    private UUID uuid;
    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;
    private Status status;
    private ReportType type;
    private String description;
    private ReportParams params;
    private UUID userId;

    public ReportBuilder() {
    }

    public ReportBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public ReportBuilder setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public ReportBuilder setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public ReportBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public ReportBuilder setType(ReportType type) {
        this.type = type;
        return this;
    }

    public ReportBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ReportBuilder setParams(ReportParams params) {
        this.params = params;
        return this;
    }

    public ReportBuilder setUserId(UUID userId) {
        this.userId = userId;
        return this;
    }

    public static ReportBuilder create(){return new ReportBuilder();}
    public Report build(){
        return new Report(uuid,
        dtCreate,
        dtUpdate,
        status,
        type,
        description,
        params,
        userId);
    }
}
