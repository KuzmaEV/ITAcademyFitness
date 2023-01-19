package by.mk_jd2_92_22.reportService.controllers;

import by.mk_jd2_92_22.reportService.dto.PageDTO;
import by.mk_jd2_92_22.reportService.dto.ReportParams;
import by.mk_jd2_92_22.reportService.model.Report;
import by.mk_jd2_92_22.reportService.model.ReportType;
import by.mk_jd2_92_22.reportService.service.api.IReportService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;


@RestController
@RequestMapping("/report")
public class ReportController {

    private final IReportService service;

    public ReportController(IReportService service) {
        this.service = service;
    }


    @PostMapping("/{type}/profile/{profile_uuid}")
    public ResponseEntity<String> create(@Valid @RequestBody ReportParams params,
                                         @PathVariable("type") ReportType type,
                                         @PathVariable("profile_uuid") UUID profile,
                                         @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){

        this.service.create(params, type, profile, token);
        return new ResponseEntity<>("Отчет запущен", HttpStatus.CREATED);
    }

    @GetMapping("/profile/{profile_uuid}")
    public ResponseEntity<PageDTO<Report>> get(@RequestParam int page,
                                               @RequestParam int size,
                                               @PathVariable("profile_uuid") UUID profile,
                                               @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){
        return ResponseEntity.ok(this.service.get(page, size, profile, token));}

//    @GetMapping("/{uuid}/export")
//    public ResponseEntity<Report> get(@PathVariable UUID uuid){
//
//        return ResponseEntity.ok();
//    }

    @RequestMapping(method = RequestMethod.HEAD, value = "{uuid}/export")
    public ResponseEntity<?> head(@PathVariable UUID uuid,
                                  @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){
        this.service.validation(uuid, token);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

