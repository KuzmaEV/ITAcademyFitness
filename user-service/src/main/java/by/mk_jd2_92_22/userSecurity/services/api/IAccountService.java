package by.mk_jd2_92_22.userSecurity.services.api;

import by.mk_jd2_92_22.userSecurity.model.UserMe;
import by.mk_jd2_92_22.userSecurity.model.dto.LoginDTO;

public interface IAccountService {
    void registration(LoginDTO item);
    String login(LoginDTO dto);
    UserMe me();
}
