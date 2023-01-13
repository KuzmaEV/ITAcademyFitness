package by.mk_jd2_92_22.foodCounter.controllers;

import by.mk_jd2_92_22.foodCounter.services.api.IProfileService;
import by.mk_jd2_92_22.foodCounter.model.Profile;
import by.mk_jd2_92_22.foodCounter.services.dto.ProfileDTO;
import by.mk_jd2_92_22.foodCounter.services.dto.ProfileResponseDTO;
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
    public ResponseEntity<Profile> create(@Valid @RequestBody ProfileDTO dto){
        return ResponseEntity.ok(service.create(dto));
    }
    @GetMapping("/{uuid_profile}")
    public ResponseEntity<ProfileResponseDTO> get(@PathVariable("uuid_profile") UUID uuid){
        return ResponseEntity.ok(service.get(uuid));
    }

    @PutMapping("/{uuid_profile}/dt_update/{dt_update}")
    public ResponseEntity<Profile> update(@PathVariable("uuid_profile") UUID uuid,
                                          @PathVariable("dt_update") LocalDateTime dtUpdate,
                                          @Valid @RequestBody ProfileDTO dto){
        return ResponseEntity.ok(service.update(uuid, dtUpdate, dto));
    }

    @DeleteMapping("/{uuid_profile}/dt_update/{dt_update}")
    public void delete(@PathVariable("uuid_profile") UUID uuid,
            @PathVariable("dt_update") LocalDateTime dtUpdate){

        service.delete(uuid, dtUpdate);
    }


}
