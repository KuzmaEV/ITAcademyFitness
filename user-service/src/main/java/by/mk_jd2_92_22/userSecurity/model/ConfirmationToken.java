package by.mk_jd2_92_22.userSecurity.model;

import by.mk_jd2_92_22.userSecurity.model.UserFull;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "confirmation_token")
public class ConfirmationToken {

    @Id
    private UUID uuid;

    @Column(name = "dt_create")
    private LocalDateTime dtCreate;
    @Column(name = "dt_expires")
    private LocalDateTime dtExpires;

    @Column(name = "dt_confirmed")
    private LocalDateTime dtConfirmed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserFull user;

    public ConfirmationToken() {
    }

    public ConfirmationToken(UUID uuid, LocalDateTime dtCreate, LocalDateTime dtExpires, UserFull user) {
        this.uuid = uuid;
        this.dtCreate = dtCreate;
        this.dtExpires = dtExpires;
        this.user = user;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public LocalDateTime getDtCreate() {
        return dtCreate;
    }

    public void setDtCreate(LocalDateTime dtCreate) {
        this.dtCreate = dtCreate;
    }

    public LocalDateTime getDtExpires() {
        return dtExpires;
    }

    public void setDtExpires(LocalDateTime dtExpires) {
        this.dtExpires = dtExpires;
    }

    public LocalDateTime getDtConfirmed() {
        return dtConfirmed;
    }

    public void setDtConfirmed(LocalDateTime dtConfirmed) {
        this.dtConfirmed = dtConfirmed;
    }

    public UserFull getUser() {
        return user;
    }

    public void setUser(UserFull user) {
        this.user = user;
    }
}
