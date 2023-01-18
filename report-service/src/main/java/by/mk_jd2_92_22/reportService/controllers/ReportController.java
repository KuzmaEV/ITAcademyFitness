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


@RestController("/report")
public class ReportController {

    private final IReportService service;

    public ReportController(IReportService service) {
        this.service = service;
    }


    @PostMapping("/{type}")
    public ResponseEntity<String> create(@Valid @RequestBody ReportParams params,
                                  @PathVariable ReportType type,
                                  @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){

        this.service.create(params, type, token);
        return new ResponseEntity<>("Отчет запущен", HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<PageDTO<Report>> get(@RequestParam int page,
                                               @RequestParam int size){
        return ResponseEntity.ok(this.service.get(page, size));}

//    @GetMapping("/{uuid}/export")
//    public ResponseEntity<Report> get(@PathVariable UUID uuid){
//
//        return ResponseEntity.ok();
//    }
//
//    @RequestMapping(method = RequestMethod.HEAD, value = "{uuid}/export")
//    public ResponseEntity<?> head(@PathVariable UUID uuid){
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}

