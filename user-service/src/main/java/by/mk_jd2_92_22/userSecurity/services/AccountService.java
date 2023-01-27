package by.mk_jd2_92_22.userSecurity.services;

import by.mk_jd2_92_22.userSecurity.model.*;
import by.mk_jd2_92_22.userSecurity.model.dto.LoginDTO;
import by.mk_jd2_92_22.userSecurity.security.JwtProvider;
import by.mk_jd2_92_22.userSecurity.services.api.IAccountService;
import by.mk_jd2_92_22.userSecurity.services.api.IUserService;
import by.mk_jd2_92_22.userSecurity.services.util.AuditProvider;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional(readOnly = true)
public class AccountService implements IAccountService {

    private final JwtProvider jwtProvider;
    private final IUserService userService;
    private final PasswordEncoder encoder;
    private final UserHolder holder;
    private final AuditProvider createAudit;

    public AccountService(JwtProvider jwtProvider, IUserService userService, PasswordEncoder encoder,
                          UserHolder holder, AuditProvider createAudit) {
        this.jwtProvider = jwtProvider;
        this.userService = userService;
        this.encoder = encoder;
        this.holder = holder;
        this.createAudit = createAudit;
    }

    @Override
    @Transactional
    public String login(LoginDTO dto){


        final UserFull user = this.userService.findByMail(dto.getMail()).orElseThrow(() ->
                new IllegalStateException("Неверный логин или пароль"));

        //Проверяем пороль
        if(!this.encoder.matches(dto.getPassword(), user.getPassword())){
            throw new IllegalStateException("Неверный логин или пароль");
        }

        if (!user.getStatus().equals(Status.ACTIVATED)){
            throw new IllegalStateException("Вход запрещен");
        }

        final String token = this.jwtProvider.createToken(user.getMail());

        this.createAudit.create(user.getUuid(), user.getRole().name() + " login", token);

        return token;
    }

    @Override
    public UserMe me(){

        final UserDetails userDetails = this.holder.getUser();
        final UserFull myUser = this.userService.findByMail(userDetails.getUsername()).orElseThrow(() ->
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
