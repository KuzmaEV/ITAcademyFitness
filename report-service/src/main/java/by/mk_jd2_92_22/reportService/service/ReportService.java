package by.mk_jd2_92_22.reportService.service;

import by.mk_jd2_92_22.reportService.dto.ProfileDTO;
import by.mk_jd2_92_22.reportService.minio.MinioService;
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
import by.mk_jd2_92_22.reportService.service.utils.AuditProvider;
import by.mk_jd2_92_22.reportService.service.utils.ProviderMicroservice;
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
import java.util.List;
import java.util.UUID;

@Service
public class ReportService implements IReportService {

    private final IReportRepository dao;
    private final UserHolder holder;
    private final AuditProvider auditProvider;
    private final MapperPageDTO<Report> mapperPageDTO;
    private final ProviderMicroservice getFromAnotherService;
    private final MinioService minioComponent;


    public ReportService(IReportRepository dao, UserHolder holder, AuditProvider auditProvider,
                         MapperPageDTO<Report> mapperPageDTO, ProviderMicroservice getFromAnotherService,
                         MinioService minioComponent) {
        this.dao = dao;
        this.holder = holder;
        this.auditProvider = auditProvider;
        this.mapperPageDTO = mapperPageDTO;
        this.getFromAnotherService = getFromAnotherService;
        this.minioComponent = minioComponent;
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
                .setEmail(this.holder.getUser().getEmail())
                .build();

        this.dao.save(report);

        this.auditProvider.create(userId, "new Report created", Type.REPORT, token);
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

        final Page<Report> reportPage = this.dao.findAllByProfileId(pageable, profileId);

        return this.mapperPageDTO.mapper(reportPage);
    }

    @Override
    @Transactional(readOnly = true)
    public void validation(UUID reportUuid, HttpHeaders token){

        final UUID userId = UUID.fromString(this.holder.getUser().getUsername());


        final Report report = this.dao.findByUuidAndStatus(reportUuid, Status.DONE).orElseThrow(() ->
                new NoContentException("Сервер, по предоставленному uuid, не смог найти информацию"));

        //TODO такая проверка норм?
        final UUID profileId = report.getProfileId();//TODO создать метод ВалидПрофиль (холдер + эта хрень)
        final ProfileDTO profileDTO = this.getFromAnotherService.getProfile(token, profileId);
        final UUID userIdProfile = profileDTO.getUser().getUuid();
        if (!userId.equals(userIdProfile)) {
            throw new IllegalStateException("Вам не принадлежит этот профиль");
        }

    }

    @Override
    @Transactional(readOnly = true)
    public  byte[] export(UUID reportUuid, HttpHeaders token){

        final UUID userId = UUID.fromString(this.holder.getUser().getUsername());


        final Report report = this.dao.findByUuidAndStatus(reportUuid, Status.DONE).orElseThrow(() ->
                new NoContentException("Сервер, по предоставленному uuid, не смог найти информацию"));

        final UUID profileId = report.getProfileId();
        final ProfileDTO profileDTO = this.getFromAnotherService.getProfile(token, profileId);
        final UUID userIdProfile = profileDTO.getUser().getUuid();
        if (!userId.equals(userIdProfile)) {
            throw new IllegalStateException("Вам не принадлежит этот профиль");
        }

        final String nameFile = report.getUuid().toString();
        return this.minioComponent.get(nameFile);
    }

    @Override
    @Transactional
    public void updateStatus(UUID reportId, LocalDateTime dtUpdate, Status status){
        Report report = this.dao.findById(reportId).orElseThrow(() ->
                new IllegalArgumentException("Не удалось найти отчет при изменении статуса"));

        if (!report.getDtUpdate().isEqual(dtUpdate)){
            if (report.getStatus().equals(Status.PROGRESS)){
                report.setStatus(Status.LOADED);
                this.dao.save(report);
            }
            throw new IllegalArgumentException("Не совпадают версии обнавления");
        }

        report.setStatus(status);
        this.dao.save(report);

    }

    @Override
    @Transactional(readOnly = true)
    public Report getLoaded(){

        Pageable pageable = PageRequest.of(0, 1, Sort.by("dtUpdate"));

        Page<Report> reportPage = dao.findAllByStatus(Status.LOADED, pageable);

        List<Report> content = reportPage.getContent();
        if (!content.isEmpty()){
            return content.get(0);
        }
        //TODO что делать с джобай, как остановить если пусто
        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Report get(UUID reportId) {

        return this.dao.findById(reportId).orElseThrow(() ->
                new IllegalArgumentException("Ошибка при получении данный об отчете из БД"));
    }
}
