package by.mk_jd2_92_22.userSecurity.model.dto;

import by.mk_jd2_92_22.userSecurity.model.Role;
import by.mk_jd2_92_22.userSecurity.model.Status;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class AdminDTO {

    @NotBlank(message = "Email is mandatory")
    private String mail;
    @NotBlank(message = "Nick is mandatory")
    private String nick;
    @NotNull(message = "Role is mandatory")
    private Role role;
    @NotNull(message = "Status is mandatory")
    private Status status;
    @NotBlank(message = "Password is mandatory")
    private String password;

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
