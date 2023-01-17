package by.mk_jd2_92_22.foodCounter.controllers;

import by.mk_jd2_92_22.foodCounter.model.JournalFood;
import by.mk_jd2_92_22.foodCounter.services.api.IJournalFoodService;
import by.mk_jd2_92_22.foodCounter.services.dto.JournalFoodDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.PageDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/journal/food")
public class JournalFoodController {

    private final IJournalFoodService service;

    public JournalFoodController(IJournalFoodService service) {
        this.service = service;
    }


    @PostMapping
    ResponseEntity<JournalFood> create(@Valid @RequestBody JournalFoodDTO dto,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){
        return ResponseEntity.ok(service.create(dto, token));
    }

    @GetMapping
    ResponseEntity<PageDTO<JournalFood>> get(@RequestParam int page,
                                @RequestParam int size){
        return ResponseEntity.ok(service.get(page, size));
    }

    @GetMapping
    @RequestMapping("/{uuid}")
    ResponseEntity<JournalFood> get(@PathVariable UUID uuid){
        return ResponseEntity.ok(service.get(uuid));
    }


    @PutMapping("/{uuid}/dt_update/{dt_update}")
    ResponseEntity<JournalFood> update(@PathVariable UUID uuid,
                                       @PathVariable ("dt_update") LocalDateTime dtUpdate,
                                       @Valid @RequestBody JournalFoodDTO dto,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){

        JournalFood product = service.update(uuid, dtUpdate, dto, token);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{uuid}/dt_update/{dt_update}")
    ResponseEntity<?> delete(@PathVariable UUID uuid,
                             @PathVariable ("dt_update") LocalDateTime dtUpdate,
                             @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){

        service.delete(uuid, dtUpdate, token);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
