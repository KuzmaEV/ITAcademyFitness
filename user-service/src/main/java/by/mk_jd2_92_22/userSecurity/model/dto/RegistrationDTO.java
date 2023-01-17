package by.mk_jd2_92_22.userSecurity.model.dto;

import javax.validation.constraints.NotBlank;

public class RegistrationDTO extends LoginDTO{

    @NotBlank(message = "Nick is mandatory")
    private String nick;


    public String getNick() {
        return nick;
    }

}
