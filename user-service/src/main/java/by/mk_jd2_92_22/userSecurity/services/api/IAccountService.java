package by.mk_jd2_92_22.userSecurity.services.api;

import by.mk_jd2_92_22.userSecurity.model.UserMe;
import by.mk_jd2_92_22.userSecurity.model.dto.LoginDTO;
import by.mk_jd2_92_22.userSecurity.model.dto.RegistrationDTO;

public interface IAccountService {
//    void registration(RegistrationDTO item);
    String login(LoginDTO dto);
    UserMe me();
}
