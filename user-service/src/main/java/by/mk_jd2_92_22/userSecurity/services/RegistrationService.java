package by.mk_jd2_92_22.userSecurity.services;

import by.mk_jd2_92_22.userSecurity.model.Role;
import by.mk_jd2_92_22.userSecurity.model.Status;
import by.mk_jd2_92_22.userSecurity.model.UserFull;
import by.mk_jd2_92_22.userSecurity.model.builder.MyUserBuilder;
import by.mk_jd2_92_22.userSecurity.model.dto.RegistrationDTO;
import by.mk_jd2_92_22.userSecurity.services.api.IEmailService;
import by.mk_jd2_92_22.userSecurity.repositories.UserFullRepository;
import by.mk_jd2_92_22.userSecurity.security.JwtProvider;
import by.mk_jd2_92_22.userSecurity.services.api.IConfirmationTokenService;
import by.mk_jd2_92_22.userSecurity.services.api.IRegistrationService;
import by.mk_jd2_92_22.userSecurity.services.util.AuditProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class RegistrationService implements IRegistrationService {

    private final PasswordEncoder encoder;
    private final UserFullRepository dao;
    private final IConfirmationTokenService tokenService;
    private final AuditProvider auditProvider;
    private final JwtProvider jwtProvider;
    private final IEmailService emailService;

    public RegistrationService(PasswordEncoder encoder, UserFullRepository dao, IConfirmationTokenService tokenService,
                               AuditProvider auditProvider, JwtProvider jwtProvider, IEmailService emailService) {
        this.encoder = encoder;
        this.dao = dao;
        this.tokenService = tokenService;
        this.auditProvider = auditProvider;
        this.jwtProvider = jwtProvider;
        this.emailService = emailService;
    }

    @Override
    @Transactional
    public String register(RegistrationDTO dto) {

        String mail = dto.getMail();

        if (this.dao.existsByMail(mail)){

            UserFull userFull = this.dao.findByMail(mail).orElseThrow(()->
                     new IllegalArgumentException("Чтото пошло не так. Попробуйте еще раз или обратитесь к Админу"));
            final String token = this.tokenService.valid(userFull);

            userFull.setNick(dto.getNick());
            userFull.setPassword(dto.getPassword());

            this.emailService.sendSimpleEmail(mail, token);
            return "На Вашу почту выслано письмо для подтверждения регистрации";
        }

        final LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        final UUID uuid = UUID.randomUUID();

        UserFull user = MyUserBuilder.create().setUuid(uuid)
                .setDtCreate(now)
                .setDtUpdate(now)
                .setMail(mail)
                .setNick(dto.getNick())
                .setRole(Role.USER)
                .setStatus(Status.WAITING_ACTIVATION)
                .setPassword(this.encoder.encode(dto.getPassword()))
                .build();


        UserFull save = this.dao.save(user);

        String token = this.tokenService.create(save);

        this.emailService.sendSimpleEmail(mail, token);
        return "На Вашу почту выслано письмо для подтверждения регистрации";


    }

    @Override
    @Transactional
    public String confirm(String token) {

        UserFull user = this.tokenService.confirm(token);
        if (!user.getStatus().equals(Status.WAITING_ACTIVATION)){
            throw new IllegalStateException("Пользаватель уже активирован или деактивирован");
        }

        user.setStatus(Status.ACTIVATED);
        final UserFull userFull = this.dao.save(user);

        final String tokenForAudit = this.jwtProvider.createToken(user.getMail());
        this.auditProvider.create(userFull.getUuid(), "Confirmation of registration", tokenForAudit);


        return "Подтверждение регистрации прошла успешно";
    }
}
