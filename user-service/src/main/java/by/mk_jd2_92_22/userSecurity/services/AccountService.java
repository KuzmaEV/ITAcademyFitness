package by.mk_jd2_92_22.userSecurity.services;

import by.mk_jd2_92_22.userSecurity.dao.UserFullRepository;
import by.mk_jd2_92_22.userSecurity.model.*;
import by.mk_jd2_92_22.userSecurity.model.builder.MyUserBuilder;
import by.mk_jd2_92_22.userSecurity.model.dto.LoginDTO;
import by.mk_jd2_92_22.userSecurity.security.JwtProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
public class AccountService {

    private final CustomUserDetailsService detailsService;
    private final JwtProvider jwtProvider;
    private final UserFullRepository dao;
    private final PasswordEncoder encoder;
    private final UserHolder holder;


    public AccountService(CustomUserDetailsService detailsService,
                          JwtProvider jwtProvider,
                          UserFullRepository dao,
                          PasswordEncoder encoder,
                          UserHolder holder) {
        this.detailsService = detailsService;
        this.jwtProvider = jwtProvider;
        this.dao = dao;
        this.encoder = encoder;
        this.holder = holder;
    }

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

        this.dao.save(user);
    }

    @Transactional
    public String login(LoginDTO dto){

        final UserDetails user = detailsService.loadUserByUsername(dto.getMail());

        if(!encoder.matches(dto.getPassword(), user.getPassword())){
            throw new IllegalArgumentException("Пароль неверный");
        }

        return jwtProvider.createToken(user.getUsername());
    }

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
