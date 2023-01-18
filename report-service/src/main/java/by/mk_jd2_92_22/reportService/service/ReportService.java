package by.mk_jd2_92_22.reportService.service;

import by.mk_jd2_92_22.reportService.service.builders.ReportBuilder;
import by.mk_jd2_92_22.reportService.dto.PageDTO;
import by.mk_jd2_92_22.reportService.dto.ReportParams;
import by.mk_jd2_92_22.reportService.dto.Type;
import by.mk_jd2_92_22.reportService.model.Report;
import by.mk_jd2_92_22.reportService.model.ReportType;
import by.mk_jd2_92_22.reportService.model.Status;
import by.mk_jd2_92_22.reportService.repositories.IReportRepository;
import by.mk_jd2_92_22.reportService.security.customDatail.UserHolder;
import by.mk_jd2_92_22.reportService.service.api.IReportService;
import by.mk_jd2_92_22.reportService.service.mappers.MapperPageDTO;
import by.mk_jd2_92_22.reportService.service.utils.CreatingAudit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class ReportService implements IReportService {

    private final IReportRepository dao;
    private final UserHolder holder;
    private final CreatingAudit creatingAudit;
    private final MapperPageDTO<Report> mapperPageDTO;

    public ReportService(IReportRepository dao, UserHolder holder,
                         CreatingAudit creatingAudit, MapperPageDTO<Report> mapperPageDTO) {
        this.dao = dao;
        this.holder = holder;
        this.creatingAudit = creatingAudit;
        this.mapperPageDTO = mapperPageDTO;
    }

    @Override
    @Transactional
    public void create(ReportParams params, ReportType type, HttpHeaders token){

        final UUID uuid = UUID.randomUUID();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");//TODO local??
        final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final String description = "Дневник питания за: "
                + params.getDtFrom().format(formatter) + " - " + params.getDtTo().format(formatter);
        final UUID userId = UUID.fromString(this.holder.getUser().getUsername());

        final Report report = ReportBuilder.create().setUuid(uuid)
                .setDtCreate(now)
                .setDtUpdate(now)
                .setStatus(Status.LOADED)
                .setType(type)
                .setDescription(description)
                .setParams(params)
                .setUserId(userId)
                .build();

        final Report save = this.dao.save(report);

        this.creatingAudit.create(userId, "new Report created", Type.REPORT, token);
    }

    @Override
    @Transactional(readOnly = true)
    public PageDTO<Report> get(int page, int size){

        final UUID userId = UUID.fromString(this.holder.getUser().getUsername());
        final Pageable pageable = PageRequest.of(page, size, Sort.by("dtUpdate").descending());

        final Page<Report> reportPage = this.dao.findAllByUserId(pageable, userId);

        return this.mapperPageDTO.mapper(reportPage);
    }
}
