package by.mk_jd2_92_22.userSecurity.model.dto;

import javax.validation.constraints.NotBlank;

public class LoginDTO {

    @NotBlank(message = "Email is mandatory")
    private String mail;
    @NotBlank(message = "Nick is mandatory")
    private String nick;
    @NotBlank(message = "Password is mandatory")
    private String password;

    public String getMail() {
        return mail;
    }

    public String getNick() {
        return nick;
    }

    public String getPassword() {
        return password;
    }
}
