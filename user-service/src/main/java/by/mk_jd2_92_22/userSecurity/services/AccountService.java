package by.mk_jd2_92_22.userSecurity.services;

import by.mk_jd2_92_22.userSecurity.dao.UserFullRepository;
import by.mk_jd2_92_22.userSecurity.model.*;
import by.mk_jd2_92_22.userSecurity.model.builder.MyUserBuilder;
import by.mk_jd2_92_22.userSecurity.model.dto.AuditDTO;
import by.mk_jd2_92_22.userSecurity.model.dto.LoginDTO;
import by.mk_jd2_92_22.userSecurity.model.dto.Type;
import by.mk_jd2_92_22.userSecurity.security.JwtProvider;
import by.mk_jd2_92_22.userSecurity.services.api.IAccountService;
import org.springframework.http.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AccountService implements IAccountService {

    private final JwtProvider jwtProvider;
    private final UserFullRepository dao;
    private final PasswordEncoder encoder;
    private final UserHolder holder;
    private final RestTemplate restTemplate;


    public AccountService(JwtProvider jwtProvider,
                          UserFullRepository dao, PasswordEncoder encoder, UserHolder holder,
                          RestTemplate restTemplate) {
        this.jwtProvider = jwtProvider;
        this.dao = dao;
        this.encoder = encoder;
        this.holder = holder;
        this.restTemplate = restTemplate;
    }


    @Override
    @Transactional
    public void registration(LoginDTO item){

        final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final UUID uuid = UUID.randomUUID();

        UserFull user = MyUserBuilder.create().setUuid(uuid)
                .setDtCreate(now)
                .setDtUpdate(now)
                .setMail(item.getMail())
                .setNick(item.getNick())
                .setRole(Role.USER)
                .setStatus(Status.ACTIVATED)
                .setPassword(encoder.encode(item.getPassword()))
                .build();

//TODO подтверждение регистрации через email
        this.dao.save(user);


    }

    @Override
    @Transactional
    public String login(LoginDTO dto){


        final UserFull user = this.dao.findByMail(dto.getMail()).orElseThrow(() ->
                new IllegalStateException("Неверный логин или пароль"));

        if(!encoder.matches(dto.getPassword(), user.getPassword())){
            throw new IllegalStateException("Неверный логин или пароль");
        }
        final AuditDTO auditDTO = new AuditDTO(user.getUuid(), "User login", Type.USER);
        final String url = "http://audit-service:8080/audit";
        final String token = jwtProvider.createToken(user.getMail());
//TODO Authentication with the RestTemplate

        //TODO HttpEntity в отдельный класс
        String tokenHead = "Bearer " + token;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
		headers.set("Authorization", tokenHead);
        HttpEntity<AuditDTO> jwtEntity = new HttpEntity<>(auditDTO, headers);


        try {
            final ResponseEntity<String> responseEntity = restTemplate
                    .exchange(url,
                    HttpMethod.POST, jwtEntity, String.class);
//                    .postForEntity(url, auditDTO, String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Не удалось создать audit: " + e);
        }

        //TODO проверить исключения

//        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
//
//                throw new IllegalArgumentException("Не удалось создать audit ");
//            }

        return token;
    }

    @Override
    public UserMe me(){

        final UserDetails userDetails = holder.getUser();
        final UserFull myUser = dao.findByMail(userDetails.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException("User не найден"));

        return new UserMe(myUser.getUuid(),
                myUser.getDtCreate(),
                myUser.getDtUpdate(),
                myUser.getMail(),
                myUser.getNick(),
                myUser.getRole(),
                myUser.getStatus());
    }

}
