package by.mk_jd2_92_22.foodCounter.controllers;

import by.mk_jd2_92_22.foodCounter.model.JournalFood;
import by.mk_jd2_92_22.foodCounter.model.JournalFoodList;
import by.mk_jd2_92_22.foodCounter.services.api.IJournalFoodService;
import by.mk_jd2_92_22.foodCounter.services.dto.JournalFoodDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.PageDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/profile")
public class JournalFoodController {

    private final IJournalFoodService service;

    public JournalFoodController(IJournalFoodService service) {
        this.service = service;
    }


    @PostMapping("/{uuid_profile}/journal/food")
    ResponseEntity<JournalFood> create(@Valid @RequestBody JournalFoodDTO dto,
                                       @PathVariable("uuid_profile") UUID profile,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){
        return ResponseEntity.ok(service.create(dto, profile, token));
    }

    @GetMapping("/{uuid_profile}/journal/food")
    ResponseEntity<PageDTO<JournalFood>> get(@RequestParam int page,
                                             @RequestParam int size,
                                             @PathVariable("uuid_profile") UUID profile){
        return ResponseEntity.ok(service.get(page, size, profile));
    }

    @GetMapping
    @RequestMapping("/{uuid_profile}/journal/food/{uuid}")
    ResponseEntity<JournalFood> get(@PathVariable UUID uuid,
                                    @PathVariable("uuid_profile") UUID profile){
        return ResponseEntity.ok(service.get(uuid, profile));
    }


    @PutMapping("/{uuid_profile}/journal/food/{uuid}/dt_update/{dt_update}")
    ResponseEntity<JournalFood> update(@PathVariable UUID uuid,
                                       @PathVariable("uuid_profile") UUID profile,
                                       @PathVariable ("dt_update") LocalDateTime dtUpdate,
                                       @Valid @RequestBody JournalFoodDTO dto,
                                       @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){

        JournalFood product = service.update(uuid, dtUpdate, dto, profile, token);
        return ResponseEntity.ok(product);
    }

    @DeleteMapping("/{uuid_profile}/journal/food/{uuid}/dt_update/{dt_update}")
    ResponseEntity<?> delete(@PathVariable UUID uuid,
                             @PathVariable("uuid_profile") UUID profile,
                             @PathVariable ("dt_update") LocalDateTime dtUpdate,
                             @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){

        service.delete(uuid, dtUpdate, profile, token);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{uuid_profile}/report")
    ResponseEntity<JournalFoodList> get(@PathVariable("uuid_profile") UUID profileId,
                                          @RequestParam LocalDate from,
                                          @RequestParam LocalDate to){

        final JournalFoodList journalFoods = this.service.get(from, to, profileId);
        return ResponseEntity.ok(journalFoods);

    }

}
