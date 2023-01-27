package by.mk_jd2_92_22.userSecurity.services;

import by.mk_jd2_92_22.userSecurity.model.ConfirmationToken;
import by.mk_jd2_92_22.userSecurity.model.Status;
import by.mk_jd2_92_22.userSecurity.model.UserFull;
import by.mk_jd2_92_22.userSecurity.repositories.IConfirmationTokenRepository;
import by.mk_jd2_92_22.userSecurity.services.api.IConfirmationTokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
@Transactional
public class ConfirmationTokenService implements IConfirmationTokenService {

    private final IConfirmationTokenRepository dao;

    @Value("${confirmation.token.expiration}")
    private int dtExpires;


    public ConfirmationTokenService(IConfirmationTokenRepository dao) {
        this.dao = dao;
    }


    @Override
    public String valid(UserFull user){
        if (user.getStatus().equals(Status.ACTIVATED) ||
                user.getStatus().equals(Status.DEACTIVATED)){
            throw new IllegalStateException("Пользователь с таким email уже существует");
    }

        ConfirmationToken confirmationToken = this.dao.findByUser(user.getUuid()).orElseThrow(() ->
                new IllegalStateException("Для подтверждения регистрации обратитесь к Админу"));

        if (confirmationToken.getDtConfirmed() != null){
            throw new IllegalStateException("Пользователь с таким email уже подтвержден");
        }

        if (LocalDateTime.now().isAfter(confirmationToken.getDtExpires())){
            confirmationToken.setDtExpires(LocalDateTime.now().plusMinutes(dtExpires));
            confirmationToken = this.dao.save(confirmationToken);
        } else {
            throw new IllegalStateException("Вам на email отпралено письмо для подтверждения регистрации");
        }


return confirmationToken.getUuid().toString();

    }

    @Override
    public String create(UserFull user){

        final UUID uuid = UUID.randomUUID();
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS);
        ConfirmationToken token = new ConfirmationToken(uuid, now, now.plusMinutes(dtExpires), user);

        ConfirmationToken save = this.dao.save(token);

        return save.getUuid().toString();
    }

    @Override
    public UserFull confirm(String token){

        final UUID uuid = UUID.fromString(token);
        ConfirmationToken confirmationToken = this.dao.findById(uuid).orElseThrow(() ->
                new IllegalStateException("Неверный токен подтверждения регистрации"));

        if (confirmationToken.getDtConfirmed() != null){
            throw new IllegalStateException("Подтверждение было быполнено ранее");
        }
        if (LocalDateTime.now().isAfter(confirmationToken.getDtExpires())){
            throw new IllegalStateException("Токен подтверждения регистрации устарел, зарегитрируйтесь еще раз");
        }

        confirmationToken.setDtConfirmed(LocalDateTime.now());
        return this.dao.save(confirmationToken).getUser();
    }
}
