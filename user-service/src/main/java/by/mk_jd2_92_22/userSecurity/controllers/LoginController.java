package by.mk_jd2_92_22.userSecurity.controllers;

import by.mk_jd2_92_22.userSecurity.model.UserMe;
import by.mk_jd2_92_22.userSecurity.model.dto.LoginDTO;
import by.mk_jd2_92_22.userSecurity.services.AccountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/users")
public class LoginController {

    private final AccountService service;

    public LoginController(AccountService service) {
        this.service = service;
    }

    @PostMapping("/registration")
    ResponseEntity<?> registration(@Valid @RequestBody LoginDTO dto){

        service.registration(dto);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

    @PostMapping("/login")
    ResponseEntity<String> login(@Valid @RequestBody LoginDTO dto){

        return ResponseEntity.ok(service.login(dto));

//возвращает токен в хедере
//        final String token = service.login(dto);
//
//        HttpHeaders responseHeaders = new HttpHeaders();
//        responseHeaders.set("Authorization",token);
//
//        return ResponseEntity.ok()
//                .headers(responseHeaders)
//                .body("response header");
    }

    @GetMapping("/me")
    ResponseEntity<UserMe> getMe(){

        return ResponseEntity.ok(service.me());
    }

    //TODO сделать logout
//    @PostMapping("/logout")
//    public void authenticate(HttpServletRequest request, HttpServletResponse response){
//        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
//        securityContextLogoutHandler.logout(request, response, null);
//    }
}
