package by.mk_jd2_92_22.userSecurity.services;

import by.mk_jd2_92_22.userSecurity.dao.UserFullRepository;
import by.mk_jd2_92_22.userSecurity.model.*;
import by.mk_jd2_92_22.userSecurity.model.builder.MyUserBuilder;
import by.mk_jd2_92_22.userSecurity.model.dto.AuditDTO;
import by.mk_jd2_92_22.userSecurity.model.dto.LoginDTO;
import by.mk_jd2_92_22.userSecurity.model.dto.Type;
import by.mk_jd2_92_22.userSecurity.security.JwtProvider;
import by.mk_jd2_92_22.userSecurity.services.api.IAccountService;
import org.springframework.http.ResponseEntity;
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

    private final CustomUserDetailsService detailsService;
    private final JwtProvider jwtProvider;
    private final UserFullRepository dao;
    private final PasswordEncoder encoder;
    private final UserHolder holder;
    private final RestTemplate restTemplate;


    public AccountService(CustomUserDetailsService detailsService, JwtProvider jwtProvider,
                          UserFullRepository dao, PasswordEncoder encoder, UserHolder holder,
                          RestTemplate restTemplate) {
        this.detailsService = detailsService;
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


        final UserFull saveUser = this.dao.save(user);


        try {


            final ResponseEntity<String> responseEntity = restTemplate
                    .postForEntity("http://audit-service:8080/audit", new AuditDTO(saveUser.getUuid(),
                            "User registration", Type.USER), String.class);
        } catch (RestClientException e) {
            e.printStackTrace();
            throw new IllegalArgumentException("Не удалось создать audit: " + e);
        }

        //TODO проверить исключения

//        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {
//
//                throw new IllegalArgumentException("Не удалось создать audit ");
//            }


    }

    @Override
    @Transactional
    public String login(LoginDTO dto){

        final UserDetails user = detailsService.loadUserByUsername(dto.getMail());

        if(!encoder.matches(dto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Пароль неверный");
        }

        return jwtProvider.createToken(user.getUsername());
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
