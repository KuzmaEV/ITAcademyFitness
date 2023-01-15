package by.mk_jd2_92_22.userSecurity.services;

import by.mk_jd2_92_22.userSecurity.repositories.UserFullRepository;
import by.mk_jd2_92_22.userSecurity.model.*;
import by.mk_jd2_92_22.userSecurity.model.builder.MyUserBuilder;
import by.mk_jd2_92_22.userSecurity.model.dto.LoginDTO;
import by.mk_jd2_92_22.userSecurity.security.JwtProvider;
import by.mk_jd2_92_22.userSecurity.services.api.IAccountService;
import by.mk_jd2_92_22.userSecurity.services.util.CreatingAudit;
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
public class AccountService implements IAccountService {

    private final JwtProvider jwtProvider;
    private final UserFullRepository dao;
    private final PasswordEncoder encoder;
    private final UserHolder holder;
    private final CreatingAudit createAudit;

    public AccountService(JwtProvider jwtProvider, UserFullRepository dao,
                          PasswordEncoder encoder, UserHolder holder, CreatingAudit createAudit) {
        this.jwtProvider = jwtProvider;
        this.dao = dao;
        this.encoder = encoder;
        this.holder = holder;
        this.createAudit = createAudit;
    }


    @Override
    @Transactional
    public void registration(LoginDTO item){

        if (dao.existsByEmail(item.getMail())){
            throw new IllegalStateException("Пользователь с таким email уже существует");
        }

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


        //Получаем пользователя из базы
        final UserFull user = this.dao.findByMail(dto.getMail()).orElseThrow(() ->
                new IllegalStateException("Неверный логин или пароль"));

        //Проверяем пороль
        if(!encoder.matches(dto.getPassword(), user.getPassword())){
            throw new IllegalStateException("Неверный логин или пароль");
        }

        final String token = jwtProvider.createToken(user.getMail());

        createAudit.create(user.getUuid(), user.getRole().name() + " login", token);

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
