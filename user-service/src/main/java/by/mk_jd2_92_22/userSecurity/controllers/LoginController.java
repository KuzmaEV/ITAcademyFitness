package by.mk_jd2_92_22.userSecurity.controllers;

import by.mk_jd2_92_22.userSecurity.model.UserMe;
import by.mk_jd2_92_22.userSecurity.model.dto.LoginDTO;
import by.mk_jd2_92_22.userSecurity.model.dto.RegistrationDTO;
import by.mk_jd2_92_22.userSecurity.services.api.IAccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class LoginController {

    private final IAccountService service;

    public LoginController(IAccountService service) {
        this.service = service;
    }

    @PostMapping("/login")
    ResponseEntity<String> login(@Valid @RequestBody LoginDTO dto) {

        return ResponseEntity.ok(service.login(dto));
    }

    @GetMapping("/me")
    ResponseEntity<UserMe> getMe(){

        return ResponseEntity.ok(service.me());
    }

}
