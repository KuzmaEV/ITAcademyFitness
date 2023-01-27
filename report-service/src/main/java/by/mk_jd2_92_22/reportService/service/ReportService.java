package by.mk_jd2_92_22.reportService.service;

import by.mk_jd2_92_22.reportService.service.dto.ProfileDTO;
import by.mk_jd2_92_22.reportService.minio.MinioService;
import by.mk_jd2_92_22.reportService.service.builders.ReportBuilder;
import by.mk_jd2_92_22.reportService.service.dto.PageDTO;
import by.mk_jd2_92_22.reportService.service.dto.ReportParams;
import by.mk_jd2_92_22.reportService.service.dto.Type;
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
@Transactional(readOnly = true)
public class ReportService implements IReportService {

    private final IReportRepository dao;
    private final UserHolder holder;
    private final AuditProvider auditProvider;
    private final MapperPageDTO<Report> mapperPageDTO;
    private final ProviderMicroservice serviceProvider;
    private final MinioService minioComponent;


    public ReportService(IReportRepository dao, UserHolder holder, AuditProvider auditProvider,
                         MapperPageDTO<Report> mapperPageDTO, ProviderMicroservice getFromAnotherService,
                         MinioService minioComponent) {
        this.dao = dao;
        this.holder = holder;
        this.auditProvider = auditProvider;
        this.mapperPageDTO = mapperPageDTO;
        this.serviceProvider = getFromAnotherService;
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

        this.validProfile(profile, token);


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
    public PageDTO<Report> get(int page, int size, UUID profileId, HttpHeaders token){

        final Pageable pageable = PageRequest.of(page, size, Sort.by("dtUpdate").descending());

        this.validProfile(profileId, token);
        final Page<Report> reportPage = this.dao.findAllByProfileId(pageable, profileId);

        return this.mapperPageDTO.mapper(reportPage);
    }

    @Override
    public void validation(UUID reportUuid, HttpHeaders token){

        final Report report = this.dao.findByUuidAndStatus(reportUuid, Status.DONE).orElseThrow(() ->
                new NoContentException("Сервер, по предоставленному uuid, не смог найти информацию"));

        final UUID profileId = report.getProfileId();
        this.validProfile(profileId, token);

    }

    @Override
    public  byte[] export(UUID reportUuid, HttpHeaders token){

        final Report report = this.dao.findByUuidAndStatus(reportUuid, Status.DONE).orElseThrow(() ->
                new NoContentException("Сервер, по предоставленному uuid, не смог найти информацию"));

        final UUID profileId = report.getProfileId();

        this.validProfile(profileId, token);

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
    public Report getLoaded(){

        Pageable pageable = PageRequest.of(0, 1, Sort.by("dtUpdate"));

        Page<Report> reportPage = dao.findAllByStatus(Status.LOADED, pageable);

        List<Report> content = reportPage.getContent();
        if (!content.isEmpty()){
            return content.get(0);
        }

        return null;
    }

    @Override
    public Report get(UUID reportId) {

        return this.dao.findById(reportId).orElseThrow(() ->
                new IllegalArgumentException("Ошибка при получении данный об отчете из БД"));
    }



    private void validProfile(UUID profileId, HttpHeaders token) {
        //TODO такая проверка норм?
        final UUID userId = UUID.fromString(this.holder.getUser().getUsername());// Достаем ИД пользователя

        final ProfileDTO profileDTO = this.serviceProvider.getProfile(token, profileId); // достаем профиль из микросервиса
        final UUID userIdProfile = profileDTO.getUser().getUuid(); // из профиля достаем ИД пользователя
        if (!userId.equals(userIdProfile)) {//сравниваем ИД вошедшего и кому принадлежит профиль
            throw new IllegalStateException("Вам не принадлежит этот профиль");
        }
    }
}
