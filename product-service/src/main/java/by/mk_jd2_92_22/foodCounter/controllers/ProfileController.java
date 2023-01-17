package by.mk_jd2_92_22.foodCounter.controllers;

import by.mk_jd2_92_22.foodCounter.services.api.IProfileService;
import by.mk_jd2_92_22.foodCounter.services.dto.PageDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.ProfileDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.ProfileResponseDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/profile")
public class ProfileController {

    private final IProfileService service;

    public ProfileController(IProfileService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ProfileResponseDTO> create(@Valid @RequestBody ProfileDTO dto,
                                                     @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){
        return ResponseEntity.ok(service.create(dto, token));
    }
    @GetMapping("/{uuid_profile}")
    public ResponseEntity<ProfileResponseDTO> get(@PathVariable("uuid_profile") UUID uuid){
        return ResponseEntity.ok(service.get(uuid));
    }

    @GetMapping
    public ResponseEntity<PageDTO<ProfileResponseDTO>> get(@RequestParam int page,
                                                           @RequestParam int size){
        return ResponseEntity.ok(this.service.get(page, size));
    }

    @PutMapping("/{uuid_profile}/dt_update/{dt_update}")
    public ResponseEntity<ProfileResponseDTO> update(@PathVariable("uuid_profile") UUID uuid,
                                          @PathVariable("dt_update") LocalDateTime dtUpdate,
                                          @Valid @RequestBody ProfileDTO dto,
                                          @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){
        return ResponseEntity.ok(service.update(uuid, dtUpdate, dto, token));
    }

    @DeleteMapping("/{uuid_profile}/dt_update/{dt_update}")
    public void delete(@PathVariable("uuid_profile") UUID uuid,
            @PathVariable("dt_update") LocalDateTime dtUpdate,
                       @RequestHeader(HttpHeaders.AUTHORIZATION) HttpHeaders token){

        service.delete(uuid, dtUpdate, token);
    }


}
