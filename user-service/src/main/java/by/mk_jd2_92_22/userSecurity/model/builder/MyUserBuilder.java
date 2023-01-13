package by.mk_jd2_92_22.userSecurity.model.builder;

import by.mk_jd2_92_22.userSecurity.model.Role;
import by.mk_jd2_92_22.userSecurity.model.Status;
import by.mk_jd2_92_22.userSecurity.model.UserFull;

import java.time.LocalDateTime;
import java.util.UUID;

public class MyUserBuilder {

    private UUID uuid;

    private LocalDateTime dtCreate;
    private LocalDateTime dtUpdate;

    private String mail;
    private String nick;
    private Role role;
    private Status status;
    private String password;

    private MyUserBuilder() {
    }

    public UUID getUuid() {
        return uuid;
    }

    public MyUserBuilder setUuid(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    public MyUserBuilder setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
        return this;
    }

    public MyUserBuilder setDtUpdate(LocalDateTime dtUpdate) {
        this.dtUpdate = dtUpdate;
        return this;
    }

    public MyUserBuilder setMail(String mail) {
        this.mail = mail;
        return this;
    }

    public MyUserBuilder setNick(String nick) {
        this.nick = nick;
        return this;
    }

    public MyUserBuilder setRole(Role role) {
        this.role = role;
        return this;
    }

    public MyUserBuilder setStatus(Status status) {
        this.status = status;
        return this;
    }

    public MyUserBuilder setPassword(String password) {
        this.password = password;
        return this;
    }

    public static MyUserBuilder create(){return new MyUserBuilder();}

    public UserFull build(){
        return  new UserFull(uuid,
                dtCreate,
                dtUpdate,
                mail,
                nick,
                role,
                status,
                password);
    }
}
