package by.mk_jd2_92_22.userSecurity.model.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class LoginDTO {

    @Email(message = "Email not valid")
    @NotBlank(message = "Email is mandatory")
    private String mail;

    @NotBlank(message = "Password is mandatory")
    private String password;


    public String getMail() {
        return mail;
    }

    public String getPassword() {
        return password;
    }
}
