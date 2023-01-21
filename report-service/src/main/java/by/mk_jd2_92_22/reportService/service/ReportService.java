package by.mk_jd2_92_22.reportService.service;

import by.mk_jd2_92_22.reportService.dto.ProfileDTO;
import by.mk_jd2_92_22.reportService.service.api.IResponseReportService;
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
import by.mk_jd2_92_22.reportService.service.exception.NoContentException;
import by.mk_jd2_92_22.reportService.service.mappers.MapperPageDTO;
import by.mk_jd2_92_22.reportService.service.utils.CreatingAudit;
import by.mk_jd2_92_22.reportService.service.utils.GetFromAnotherService;
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
    private final GetFromAnotherService getFromAnotherService;
    private final IResponseReportService responseReportService;

    public ReportService(IReportRepository dao, UserHolder holder, CreatingAudit creatingAudit,
                         MapperPageDTO<Report> mapperPageDTO, GetFromAnotherService getFromAnotherService,
                         IResponseReportService responseReportService) {
        this.dao = dao;
        this.holder = holder;
        this.creatingAudit = creatingAudit;
        this.mapperPageDTO = mapperPageDTO;
        this.getFromAnotherService = getFromAnotherService;
        this.responseReportService = responseReportService;
    }

    @Override
    @Transactional
    public void create(ReportParams params, ReportType type, UUID profile, HttpHeaders token){

        final UUID uuid = UUID.randomUUID();
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yy");
        final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final String description = "Дневник питания за: "
                + params.getDtFrom().format(formatter) + " - " + params.getDtTo().format(formatter);
        final UUID userId = UUID.fromString(this.holder.getUser().getUsername());


        final ProfileDTO profileDTO = this.getFromAnotherService.getProfile(token, profile);
        final UUID userIdProfile = profileDTO.getUser().getUuid();

        if (!userId.equals(userIdProfile)) {
            throw new IllegalStateException("Вам не принадлежит этот профиль");
        }

        final Report report = ReportBuilder.create().setUuid(uuid)
                .setDtCreate(now)
                .setDtUpdate(now)
                .setStatus(Status.LOADED)
                .setType(type)
                .setDescription(description)
                .setParams(params)
                .setProfileId(profile)
                .build();

        final Report save = this.dao.save(report);

        this.creatingAudit.create(userId, "new Report created", Type.REPORT, token);
    }

    @Override
    @Transactional(readOnly = true)
    public PageDTO<Report> get(int page, int size, UUID profileId, HttpHeaders token){

        final UUID userId = UUID.fromString(this.holder.getUser().getUsername());
        final Pageable pageable = PageRequest.of(page, size, Sort.by("dtUpdate").descending());

        final ProfileDTO profileDTO = this.getFromAnotherService.getProfile(token, profileId);
        final UUID userIdProfile = profileDTO.getUser().getUuid();

        if (!userId.equals(userIdProfile)) {
            throw new IllegalStateException("Вам не принадлежит этот профиль");
        }

        final Page<Report> reportPage = this.dao.findAllByprofileId(pageable, profileId);

        return this.mapperPageDTO.mapper(reportPage);
    }

    @Override
    @Transactional(readOnly = true)
    public void validation(UUID reportUuid, HttpHeaders token){

//        final UUID userUuid = UUID.fromString(this.holder.getUser().getUsername());


        final Report report = this.dao.findByUuidAndStatus(reportUuid, Status.DONE).orElseThrow(() ->
                new NoContentException("Сервер, по предоставленному uuid, не смог найти информацию"));

//        final UUID profileId = report.getProfileId();

    }

    @Override
    @Transactional(readOnly = true)
    public  byte[] get(UUID reportUuid, HttpHeaders token){

//        final UUID userUuid = UUID.fromString(this.holder.getUser().getUsername());


        final Report report = this.dao.findByUuidAndStatus(reportUuid, Status.DONE).orElseThrow(() ->
                new NoContentException("Сервер, по предоставленному uuid, не смог найти информацию"));

//        final UUID profileId = report.getProfileId();


        return this.responseReportService.generateReport(report, token);
    }


}
