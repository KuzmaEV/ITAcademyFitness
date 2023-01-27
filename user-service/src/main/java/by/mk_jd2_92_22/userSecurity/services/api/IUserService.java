package by.mk_jd2_92_22.userSecurity.services.api;

import by.mk_jd2_92_22.userSecurity.model.UserFull;
import by.mk_jd2_92_22.userSecurity.model.UserMe;
import by.mk_jd2_92_22.userSecurity.model.dto.AdminDTO;

import java.util.Optional;

public interface IUserService extends IService<UserMe, AdminDTO> {
    Optional<UserFull> findByMail(String mail);
}
