package by.mk_jd2_92_22.userSecurity.services.api;

import by.mk_jd2_92_22.userSecurity.model.UserFull;

public interface IConfirmationTokenService {

    String valid(UserFull user);
    String create(UserFull user);
    UserFull confirm(String token);
}
