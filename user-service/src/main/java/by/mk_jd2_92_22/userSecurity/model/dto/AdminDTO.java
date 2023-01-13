package by.mk_jd2_92_22.userSecurity.model.dto;

import by.mk_jd2_92_22.userSecurity.model.Role;
import by.mk_jd2_92_22.userSecurity.model.Status;

public class AdminDTO {

    private String mail;
    private String nick;
    private Role role;
    private Status status;
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
