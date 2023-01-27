package by.mk_jd2_92_22.userSecurity.services.api;

import by.mk_jd2_92_22.userSecurity.model.dto.RegistrationDTO;

public interface IRegistrationService {

    String register(RegistrationDTO dto);

    String confirm(String token);


}
