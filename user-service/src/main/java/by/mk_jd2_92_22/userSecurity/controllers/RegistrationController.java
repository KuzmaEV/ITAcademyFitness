package by.mk_jd2_92_22.userSecurity.controllers;

import by.mk_jd2_92_22.userSecurity.model.dto.RegistrationDTO;
import by.mk_jd2_92_22.userSecurity.services.api.IRegistrationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users/registration")
public class RegistrationController {

    private final IRegistrationService service;

    public RegistrationController(IRegistrationService service) {
        this.service = service;
    }

    @PostMapping
    ResponseEntity<String> register(@Valid @RequestBody RegistrationDTO dto){

        String message = service.register(dto);
        return new ResponseEntity<>(message, HttpStatus.ACCEPTED);
    }

    @GetMapping
    ResponseEntity<String> confirm(@RequestParam("token") String token){

        String confirm = service.confirm(token);
        return ResponseEntity.ok(confirm);
    }



}
